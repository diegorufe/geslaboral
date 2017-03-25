package com.gesLaboral.genericos.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.QueryException;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gesLaboral.genericos.dao.GenericoDao;
import com.gesLaboral.genericos.filtros.EnumTipoFiltro;
import com.gesLaboral.genericos.filtros.Filtro;

public class GenericoDaoImpl<T> implements GenericoDao<T> {

	@PersistenceContext(name="GESLABORAL")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	@Override
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		//this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(T entidad) {
		entityManager.persist(entidad);
//		entityManager.getTransaction().begin();
//		try {
//			entityManager.persist(entidad);
//			entityManager.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			entityManager.getTransaction().rollback();
//		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(T entidad) {
		entityManager.merge(entidad);
//		entityManager.getTransaction().begin();
//		try {
//			entityManager.merge(entidad);
//			entityManager.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			entityManager.getTransaction().rollback();
//		}
	}

	@Override
	public T getById(String id) {
		return entityManager.find(getClasegenerica(), id);
	}

	@Override
	public List<T> getAll() {
		return entityManager
				.createQuery("select u from " + getClasegenerica().getSimpleName() + " u", getClasegenerica())
				.getResultList();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(T entidad) {
		entityManager.remove(entityManager.contains(entidad) ? entidad : entityManager.merge(entidad));
//		entityManager.getTransaction().begin();
//		try {
//			entityManager.remove(entityManager.contains(entidad) ? entidad : entityManager.merge(entidad));
//			entityManager.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			entityManager.getTransaction().rollback();
//		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getClasegenerica() {
		ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) thisType.getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getByCodigo(String codigo) {
		T entidad = null;
		Query query = entityManager
				.createQuery("from " + getClasegenerica().getSimpleName() + " e where e.codigo='" + codigo + "'");
		query.setMaxResults(1);
		if (codigo != null) {
			try {
				List<T> lista = query.getResultList();
				if (lista != null && lista.size() > 0) {
					entidad = (T) query.getResultList().get(0);
				}
			} catch (Exception e) {
				entidad = null;
			}
		}
		return entidad;
	}

	@Override
	public List<T> getBySql(String sql) {
		return entityManager.createQuery(sql, getClasegenerica()).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByModel(T model) {
		List<T> lista = null;
		if (model != null) {
			Query query = entityManager.createQuery(
					"SELECT e FROM " + getClasegenerica().getName() + " e where e = :model", getClasegenerica());
			query.setParameter("model", model);
			try {
				update(model);
				lista = query.getResultList();
			} catch (QueryException e) {
				lista = null;
			}
		}
		return lista;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAndMerge(T entidad, List<Object> atributos) {
		if (atributos != null) {
			for (Object atr : atributos) {
				entityManager.merge(atr);
			}
		}
		entityManager.persist(entidad);
//		entityManager.getTransaction().begin();
//		try {
//			if (atributos != null) {
//				for (Object atr : atributos) {
//					entityManager.merge(atr);
//				}
//			}
//			entityManager.persist(entidad);
//			entityManager.getTransaction().commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			entityManager.getTransaction().rollback();
//		}
	}

	@Override
	public CriteriaQuery<T> createQuery(LinkedHashMap<String, SortOrder> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getClasegenerica());
		Root<T> root = criteriaQuery.from(getClasegenerica());
		LinkedList<Order> orderList = new LinkedList<Order>();
		if (orders != null && orders.size() > 0) {
			LinkedHashSet<String> keysOrder = new LinkedHashSet<String>(orders.keySet());
			for (String key : keysOrder) {
				SortOrder order = orders.get(key);
				if (order != null && !order.equals(SortOrder.UNSORTED)) {
					if (order.equals(SortOrder.ASCENDING)) {
						orderList.add(criteriaBuilder.asc(root.get(key)));
					} else if (order.equals(SortOrder.DESCENDING)) {
						orderList.add(criteriaBuilder.desc(root.get(key)));
					}
				}
			}
		}
		if (orderList != null && orderList.size() > 0) {
			criteriaQuery.orderBy(orderList);
		}
		return criteriaQuery;
	}

	@Override
	public int countByQuery(LinkedHashMap<String, SortOrder> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(getClasegenerica());
		LinkedList<Order> orderList = new LinkedList<Order>();
		if (orders != null && orders.size() > 0) {
			LinkedHashSet<String> keysOrder = new LinkedHashSet<String>(orders.keySet());
			for (String key : keysOrder) {
				SortOrder order = orders.get(key);
				if (order != null && !order.equals(SortOrder.UNSORTED)) {
					if (order.equals(SortOrder.ASCENDING)) {
						orderList.add(criteriaBuilder.asc(root.get(key)));
					} else if (order.equals(SortOrder.DESCENDING)) {
						orderList.add(criteriaBuilder.desc(root.get(key)));
					}
				}
			}
		}
		if (orderList != null && orderList.size() > 0) {
			criteriaQuery.orderBy(orderList);
		}
		criteriaQuery.select(criteriaBuilder.count(root));
		Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result.intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, int... iniFin) {
		List<T> data = null;
		Query query = entityManager.createQuery(createQuery(orders));
		if (iniFin != null && iniFin.length > 0 && iniFin.length <= 2) {
			query.setFirstResult(iniFin[0]);
			query.setMaxResults(iniFin[1]);
		}
		try {
			data = query.getResultList();
		} catch (Exception e) {
			data = null;
		}
		return data;
	}

	@Override
	public T getByCodigo(String codigo, LinkedList<Filtro> filtros) {
		T entidad = null;
		String hql = "from " + getClasegenerica().getSimpleName() + " e where e.codigo='" + codigo + "'";
		String hqlFiltros;
		if (filtros != null && filtros.size() > 0) {
			hql = hql + " and ";
			hqlFiltros = getFiltrosSql(filtros, "e");
			hql = hql + hqlFiltros;
		}
		Query query = entityManager.createQuery(hql);
		if (filtros != null && filtros.size() > 0) {
			setFiltrosInQuery(filtros, query);
		}
		query.setMaxResults(1);
		if (codigo != null) {
			try {
				List<T> lista = query.getResultList();
				if (lista != null && lista.size() > 0) {
					entidad = (T) lista.get(0);
				}
			} catch (Exception e) {
				entidad = null;
			}
		}
		return entidad;
	}

	@Override
	public CriteriaQuery<T> createQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<Filtro> filtros) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getClasegenerica());
		Root<T> root = criteriaQuery.from(getClasegenerica());
		if (filtros != null && filtros.size() > 0) {
			setFiltrosInCriteria(criteriaQuery, criteriaBuilder, root, filtros);
		}
		LinkedList<Order> orderList = new LinkedList<Order>();
		if (orders != null && orders.size() > 0) {
			LinkedHashSet<String> keysOrder = new LinkedHashSet<String>(orders.keySet());
			for (String key : keysOrder) {
				SortOrder order = orders.get(key);
				if (order != null && !order.equals(SortOrder.UNSORTED)) {
					if (order.equals(SortOrder.ASCENDING)) {
						orderList.add(criteriaBuilder.asc(root.get(key)));
					} else if (order.equals(SortOrder.DESCENDING)) {
						orderList.add(criteriaBuilder.desc(root.get(key)));
					}
				}
			}
		}
		if (orderList != null && orderList.size() > 0) {
			criteriaQuery.orderBy(orderList);
		}
		return criteriaQuery;
	}

	@Override
	public int countByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<Filtro> filtros) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(getClasegenerica());
		criteriaQuery.select(criteriaBuilder.count(root));

		if (filtros != null && filtros.size() > 0) {
			setFiltrosInCriteria(criteriaQuery, criteriaBuilder, root, filtros);
		}

		LinkedList<Order> orderList = new LinkedList<Order>();
		if (orders != null && orders.size() > 0) {
			LinkedHashSet<String> keysOrder = new LinkedHashSet<String>(orders.keySet());
			for (String key : keysOrder) {
				SortOrder order = orders.get(key);
				if (order != null && !order.equals(SortOrder.UNSORTED)) {
					if (order.equals(SortOrder.ASCENDING)) {
						orderList.add(criteriaBuilder.asc(root.get(key)));
					} else if (order.equals(SortOrder.DESCENDING)) {
						orderList.add(criteriaBuilder.desc(root.get(key)));
					}
				}
			}
		}
		if (orderList != null && orderList.size() > 0) {
			criteriaQuery.orderBy(orderList);
		}
		Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result.intValue() : 0;
	}

	@Override
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<Filtro> filtros, int... iniFin) {
		List<T> data = null;
		Query query = entityManager.createQuery(createQuery(orders, filtros));
		if (iniFin != null && iniFin.length > 0 && iniFin.length <= 2) {
			query.setFirstResult(iniFin[0]);
			query.setMaxResults(iniFin[1]);
		}
		try {
			data = query.getResultList();
		} catch (Exception e) {
			data = null;
		}
		return data;
	}

	private String getFiltrosSql(LinkedList<Filtro> filtros, String aliasDefect) {
		boolean first = false;
		StringBuilder builder = new StringBuilder();
		String cond;
		String campo;
		String alias;
		String value;
		for (Filtro filtro : filtros) {
			if (filtro.getTipoFiltro() != null) {
				if (filtro.getFiltros() != null && filtro.getFiltros().size() > 0) {
					builder.append("(");
					for (Filtro filtroFiltro : filtro.getFiltros()) {
						if (filtroFiltro.getTipoFiltro2() != null
								&& filtroFiltro.getTipoFiltro2() != EnumTipoFiltro.UNDEFINED) {
							switch (filtroFiltro.getTipoFiltro2()) {
							case O:
								builder.append(" or ");
								break;
							case Y:
								builder.append(" and ");
							default:
								break;
							}
						}
						switch (filtroFiltro.getTipoFiltro()) {
						case IGUAL:
							cond = first ? " and " : " ";
							alias = " " + (filtroFiltro.getAlias() == null ? aliasDefect : filtroFiltro.getAlias()) + ".";
							campo = filtroFiltro.getCampo() + " ";
							value = ":" + filtroFiltro.getCampo() + "pr";
							builder.append(cond + alias + campo + " = " + value + " ");
							break;

						default:
							break;
						}
					}
					builder.append(")");
				} else {
					if (!first) {
						first = true;
					}
					switch (filtro.getTipoFiltro()) {
					case IGUAL:
						cond = first ? " and " : " ";
						alias = " " + (filtro.getAlias() == null ? aliasDefect : filtro.getAlias()) + ".";
						campo = filtro.getCampo() + " ";
						value = ":" + filtro.getCampo() + "pr";
						builder.append(cond + alias + campo + " = " + value + " ");
						break;

					default:
						break;
					}
				}
			}
		}
		return builder.toString();
	}

	private void setFiltrosInQuery(LinkedList<Filtro> filtros, Query query) {
		for (Filtro filtro : filtros) {
			if(filtro.getFiltros() != null && filtro.getFiltros().size() > 0){
				for (Filtro filtroFiltro : filtro.getFiltros()) {
					query.setParameter(filtroFiltro.getCampo() + "pr", filtroFiltro.getValue());
				}
			}else{
				query.setParameter(filtro.getCampo() + "pr", filtro.getValue());
			}
			
		}
	}

	@SuppressWarnings("rawtypes")
	private void setFiltrosInCriteria(CriteriaQuery criteria, CriteriaBuilder builder, Root<T> root,
			LinkedList<Filtro> filtros) {
		if (filtros != null && filtros.size() > 0) {
			LinkedList<Predicate> restrinctions = new LinkedList<Predicate>();
			Expression<String> expresion = null;
			Expression<Date> campoFecha = null;
			Date fecha1 = null;
			Date fecha2 = null;
			// Recorremos los filtros
			for (Filtro filtro : filtros) {
				if (filtro.getTipoFiltro() != null) {
					// Si no tiene filtros entre parentesis
					if (filtro.getFiltros() == null || filtro.getFiltros().size() <= 0) {
						expresion = root.get(filtro.getCampo());
						switch (filtro.getTipoFiltro()) {
						case LIKE_TODO:
							if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {

							} else {
								restrinctions.add(builder.like(expresion, "%" + filtro.getValue() + "%"));
							}
							break;
						case ENTRE:
							if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {

							} else {
								if (filtro.getValue() instanceof Date) {
									campoFecha = root.get(filtro.getCampo());
									fecha1 = (Date) filtro.getValue();
									fecha2 = (Date) filtro.getValue2();
									restrinctions.add(builder.between(campoFecha, builder.literal(fecha1),
											builder.literal(fecha2)));
								}
							}
							break;
						case IGUAL:
							if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {
								expresion = root.join(filtro.getCampo(), filtro.getJoin()).get(filtro.getCampoJoin());
								restrinctions.add(builder.equal(expresion, filtro.getValue()));
							} else {
								restrinctions.add(builder.equal(expresion, filtro.getValue()));
							}
							break;
						case DISTINTO:
							if (filtro.getCampoJoin() != null && !filtro.getCampoJoin().trim().isEmpty()) {
								expresion = root.join(filtro.getCampo(), filtro.getJoin()).get(filtro.getCampoJoin());
								restrinctions.add(builder.notEqual(expresion, filtro.getValue()));
							} else {
								restrinctions.add(builder.notEqual(expresion, filtro.getValue()));
							}
							break;
						default:
							break;
						}
						// Si tiene filtros entre parentesis
					} else if (filtro.getFiltros() != null && filtro.getFiltros().size() > 0) {
						Predicate predicate1 = null;
						Predicate predicate2 = null;
						// Recorremos los filtros entre parentesis
						for (Filtro filtroFiltro : filtro.getFiltros()) {
							predicate2 = null;
							if (filtroFiltro.getTipoFiltro() != null) {
								expresion = root.get(filtroFiltro.getCampo());

								switch (filtroFiltro.getTipoFiltro()) {
								case IGUAL:
									if (filtroFiltro.getTipoFiltro2() == null) {
										if (filtroFiltro.getCampoJoin() != null
												&& !filtroFiltro.getCampoJoin().trim().isEmpty()) {
											expresion = root.join(filtroFiltro.getCampo(), filtroFiltro.getJoin())
													.get(filtroFiltro.getCampoJoin());
											predicate1 = builder.equal(expresion, filtroFiltro.getValue());
										} else {
											predicate1 = builder.equal(expresion, filtroFiltro.getValue());
										}
									}
									if (filtroFiltro.getTipoFiltro2() != null) {

										switch (filtroFiltro.getTipoFiltro2()) {
										case Y:
											if (filtroFiltro.getCampoJoin() != null
													&& !filtroFiltro.getCampoJoin().trim().isEmpty()) {
												expresion = root.join(filtroFiltro.getCampo(), filtroFiltro.getJoin())
														.get(filtroFiltro.getCampoJoin());
												predicate2 = builder.and(predicate1,
														builder.equal(expresion, filtroFiltro.getValue()));
											} else {
												predicate2 = builder.and(predicate1,
														builder.equal(expresion, filtroFiltro.getValue()));
											}
											break;
										case O:
											if (filtroFiltro.getCampoJoin() != null
													&& !filtroFiltro.getCampoJoin().trim().isEmpty()) {
												expresion = root.join(filtroFiltro.getCampo(), filtroFiltro.getJoin())
														.get(filtroFiltro.getCampoJoin());
												predicate2 = builder.or(predicate1,
														builder.equal(expresion, filtroFiltro.getValue()));
											} else {
												predicate2 = builder.or(predicate1,
														builder.equal(expresion, filtroFiltro.getValue()));
											}
											break;
										}
									}
									break;
								case ENTRE:
									if (filtroFiltro.getValue() instanceof Date) {
										campoFecha = root.get(filtroFiltro.getCampo());
										fecha1 = (Date) filtroFiltro.getValue();
										fecha2 = (Date) filtroFiltro.getValue2();
										if (filtroFiltro.getTipoFiltro2() == null) {
											if (filtroFiltro.getCampoJoin() != null
													&& !filtroFiltro.getCampoJoin().trim().isEmpty()) {

											} else {
												predicate1 = builder.between(campoFecha, builder.literal(fecha1),
														builder.literal(fecha2));
											}
										}
										if (filtroFiltro.getTipoFiltro2() != null) {

											switch (filtroFiltro.getTipoFiltro2()) {
											case Y:
												if (filtroFiltro.getCampoJoin() != null
														&& !filtroFiltro.getCampoJoin().trim().isEmpty()) {

												} else {
													predicate2 = builder.and(predicate1, builder.between(campoFecha,
															builder.literal(fecha1), builder.literal(fecha2)));
												}
												break;
											case O:
												if (filtroFiltro.getCampoJoin() != null
														&& !filtroFiltro.getCampoJoin().trim().isEmpty()) {

												} else {
													predicate2 = builder.or(predicate1, builder.between(campoFecha,
															builder.literal(fecha1), builder.literal(fecha2)));
												}
												break;
											}
										}
									}
									break;
								}
							}
							if (predicate2 == null && predicate1 != null && !filtroFiltro.isTienenSegundaCondicion()) {
								restrinctions.add(predicate1);
								predicate1 = null;
							} else if (filtroFiltro.isTienenSegundaCondicion() && predicate2 != null) {
								predicate1 = null;
								predicate1 = predicate2;
							}
							if (predicate2 != null) {
								restrinctions.add(predicate2);
							}
						}
					}
				}
			}
			criteria.where(restrinctions.toArray(new Predicate[restrinctions.size()]));
		}
	}
}

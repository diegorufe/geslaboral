package com.gesLaboral.genericos.dao;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaQuery;

import org.primefaces.model.SortOrder;

import com.gesLaboral.genericos.filtros.Filtro;

public interface GenericoDao<T> {

	public EntityManager getEntityManager();

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory);

	public void setEntityManager(EntityManager entityManager);

	public void save(T entidad);

	public void saveAndMerge(T entidad, List<Object> atributos);

	public void update(T entidad);

	public T getById(String id);

	public List<T> getAll();

	public void delete(T entidad);

	public Class<T> getClasegenerica();

	public T getByCodigo(String codigo);
	
	public T getByCodigo(String codigo,LinkedList<Filtro> filtros);

	public List<T> getBySql(String sql);

	public List<T> getByModel(T model);

	public CriteriaQuery<T> createQuery(LinkedHashMap<String, SortOrder> orders);
	
	public CriteriaQuery<T> createQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<Filtro> filtros);

	public int countByQuery(LinkedHashMap<String, SortOrder> orders);
	
	public int countByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<Filtro> filtros);

	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, int... iniFin);
	
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders,LinkedList<Filtro> filtros, int... iniFin);
}

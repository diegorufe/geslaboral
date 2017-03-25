package com.gesLaboral.genericos.serviceImpl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.primefaces.model.SortOrder;

import com.gesLaboral.genericos.dao.GenericoDao;
import com.gesLaboral.genericos.filtros.Filtro;
import com.gesLaboral.genericos.service.GenericoService;

public class GenericoServiceImpl<T> implements GenericoService<T> {

	private GenericoDao<T> dao;
	private LinkedList<Filtro> defaultFiltros;
	
	@Override
	public GenericoDao<T> getDao() {
		return dao;
	}

	@Override
	public void setDao(GenericoDao<T> genericoDao) {
		this.dao = genericoDao;

	}

	@Override
	public void save(T entidad, String codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(T entidad) {
		dao.save(entidad);
	}

	@Override
	public void update(T entidad, String codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(T entidad) {
		this.dao.update(entidad);
	}

	@Override
	public void delete(T entidad) {
		dao.delete(entidad);
	}

	@Override
	public T getEntidad(T entidad) {
		return null;
	}

	@Override
	public List<T> getAll() {
		return dao.getAll();
	}

	@Override
	public List<T> getBySql(String sql) {
		return dao.getBySql(sql);
	}

	@Override
	public List<T> getByModel(T model) {
		return dao.getByModel(model);
	}

	@Override
	public void saveAndMergue(T entidad, List<Object> atributos) {
		dao.saveAndMerge(entidad, atributos);
	}

	@Override
	public int countByQuery(LinkedHashMap<String, SortOrder> orders) {
		return dao.countByQuery(orders);
	}

	@Override
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, int... iniFin) {
		return dao.findByQuery(orders, iniFin);
	}

	@Override
	public T getByCodigo(String codigo) {
		return dao.getByCodigo(codigo);
	}
	
	@Override
	public LinkedList<Filtro> getDefaultFiltros() {
		return defaultFiltros;
	}
	
	@Override
	public void setDefaultFiltros(LinkedList<Filtro> defaultFiltros) {
		this.defaultFiltros = defaultFiltros;
	}



	@Override
	public int countByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<Filtro> filtros) {
		LinkedList<Filtro> filtrosEnviar = new LinkedList<>();
		if(defaultFiltros != null){
			filtrosEnviar.addAll(defaultFiltros);
		}
		if(filtros != null){
			filtrosEnviar.addAll(filtros);
		}
		return dao.countByQuery(orders, filtrosEnviar);
	}

	@Override
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<Filtro> filtros, int... iniFin) {
		LinkedList<Filtro> filtrosEnviar = new LinkedList<>();
		if(defaultFiltros != null){
			filtrosEnviar.addAll(defaultFiltros);
		}
		if(filtros != null){
			filtrosEnviar.addAll(filtros);
		}
		return dao.findByQuery(orders, filtrosEnviar, iniFin);
	}

	@Override
	public T getByCodigo(String codigo, LinkedList<Filtro> filtros) {
		LinkedList<Filtro> filtrosEnviar = new LinkedList<>();
		if(defaultFiltros != null && defaultFiltros.size() > 0){
			filtrosEnviar.addAll(defaultFiltros);
		}
		if(filtros != null && filtros.size() > 0){
			filtrosEnviar.addAll(filtros);
		}
		return dao.getByCodigo(codigo, filtrosEnviar);
	}

}

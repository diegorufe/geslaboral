package com.gesLaboral.genericos.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.primefaces.model.SortOrder;

import com.gesLaboral.genericos.dao.GenericoDao;
import com.gesLaboral.genericos.filtros.Filtro;

public interface GenericoService<T> {

	public GenericoDao<T> getDao();

	public void setDao(GenericoDao<T> genericoDao);

	public void save(T entidad, String codigo);

	public void save(T entidad);

	public void update(T entidad, String codigo);

	public void update(T entidad);

	public void delete(T entidad);

	public T getEntidad(T entidad);

	public List<T> getAll();

	public List<T> getBySql(String sql);

	public List<T> getByModel(T model);

	public void saveAndMergue(T entidad, List<Object> atributos);

	public int countByQuery(LinkedHashMap<String, SortOrder> orders);

	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, int... iniFin);

	public T getByCodigo(String codigo);
	
	public LinkedList<Filtro> getDefaultFiltros();
	
	public void setDefaultFiltros(LinkedList<Filtro> defaultFiltros);
	
	public List<T> findByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<Filtro> filtros, int... iniFin);
	
	public int countByQuery(LinkedHashMap<String, SortOrder> orders, LinkedList<Filtro> filtros);
	
	public T getByCodigo(String codigo, LinkedList<Filtro> filtros);
}

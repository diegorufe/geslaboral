package com.gesLaboral.maestros.jornadas.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.gesLaboral.genericos.filtros.EnumTipoFiltro;
import com.gesLaboral.genericos.filtros.Filtro;
import com.gesLaboral.genericos.serviceImpl.GenericoServiceImpl;
import com.gesLaboral.maestros.jornadas.model.Jornada;
import com.gesLaboral.maestros.jornadas.service.JornadaService;
import com.gesLaboral.maestros.trabajadores.model.Trabajador;

public class JornadaServiceImpl extends GenericoServiceImpl<Jornada> implements JornadaService{

	@Override
	public Jornada findJornadaFechas(Trabajador trabajador, Date fechaIni, Date fechaFin) {
		Jornada jornada = null;
		if(trabajador != null && fechaIni != null && fechaIni != null){
			LinkedList<Filtro> filtros =new LinkedList<>();
			Filtro filtroTraba = new Filtro();
			filtroTraba.setCampo("trabajador");
			filtroTraba.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroTraba.setValue(trabajador);
			Filtro filtroFechas = new Filtro();
			filtroFechas.setTipoFiltro(EnumTipoFiltro.UNDEFINED);
			List<Filtro> filtrosFechas = new ArrayList<>();
			Filtro filtroFechaIni = new Filtro();
			filtroFechaIni.setTipoFiltro(EnumTipoFiltro.ENTRE);
			filtroFechaIni.setCampo("fechaInicio");
			filtroFechaIni.setTienenSegundaCondicion(true);
			filtroFechaIni.setValue(fechaIni);
			filtroFechaIni.setValue2(fechaFin);
			Filtro filtroFechaFin = new Filtro();
			filtroFechaFin.setTipoFiltro(EnumTipoFiltro.ENTRE);
			filtroFechaFin.setTipoFiltro2(EnumTipoFiltro.O);
			filtroFechaFin.setCampo("fechaFin");
			filtroFechaFin.setValue(fechaIni);
			filtroFechaFin.setValue2(fechaFin);
			filtrosFechas.add(filtroFechaIni);
			filtrosFechas.add(filtroFechaFin);
			filtroFechas.setFiltros(filtrosFechas);
			filtros.add(filtroTraba);
			filtros.add(filtroFechas);
			List<Jornada> jornadas = this.getDao().findByQuery(null, filtros, new int[]{0,5});
			if(jornadas != null && jornadas.size() > 0){
				jornada = jornadas.get(0);
			}
		}
		return jornada;
	}

}

package com.gesLaboral.maestros.jornadas.service;

import java.util.Date;

import com.gesLaboral.genericos.service.GenericoService;
import com.gesLaboral.maestros.jornadas.model.Jornada;
import com.gesLaboral.maestros.trabajadores.model.Trabajador;

public interface JornadaService extends GenericoService<Jornada>{
	public Jornada findJornadaFechas(Trabajador trabajador, Date fechaIni, Date fechaFin);
}

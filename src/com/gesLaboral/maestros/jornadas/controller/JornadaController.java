package com.gesLaboral.maestros.jornadas.controller;

import org.primefaces.event.SelectEvent;

import com.gesLaboral.genericos.controller.GenericoController;
import com.gesLaboral.maestros.jornadas.model.Jornada;
import com.gesLaboral.maestros.trabajadores.model.Trabajador;

public interface JornadaController extends GenericoController<Jornada> {
	public void onEventSelect(SelectEvent selectEvent);

	public void onDateSelect(SelectEvent selectEvent);

	public String addModel();

	public Trabajador getTrabajadorFiltro();

	public void setTrabajadorFiltro(Trabajador trabajadorFiltro);
}

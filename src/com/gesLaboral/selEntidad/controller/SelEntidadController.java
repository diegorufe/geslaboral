package com.gesLaboral.selEntidad.controller;
import org.primefaces.event.SelectEvent;

import com.gesLaboral.genericos.controller.GenericoController;

@SuppressWarnings("rawtypes")
public interface SelEntidadController extends GenericoController{
	public void initSelEmpresas();
	public void initSelTrabajador();
	public String addEntidad();
	public String delEntidad();
	void onSelect(SelectEvent event);
}

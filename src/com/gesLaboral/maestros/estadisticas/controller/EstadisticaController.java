package com.gesLaboral.maestros.estadisticas.controller;

import com.gesLaboral.genericos.controller.GenericoController;
import com.gesLaboral.maestros.empresas.model.Empresa;
import com.gesLaboral.maestros.trabajadores.model.Trabajador;
import com.gesLaboral.maestros.usuarios.model.Usuario;

@SuppressWarnings("rawtypes")
public interface EstadisticaController extends GenericoController {
	public Trabajador getTrabajadorFiltro();
	public void setTrabajadorFiltro(Trabajador trabajadorFiltro);
	public String aplicarFiltrosTrabajador();
	public String aplicarFiltrosEmpresa();
	public String aplicarFiltrosUsuario();
	public Empresa getEmpresaFiltro();
	public void setEmpresaFiltro(Empresa empresaFiltro);
	public Usuario getUsuarioFiltro();
	public void setUsuarioFiltro(Usuario usuarioFiltro);
}

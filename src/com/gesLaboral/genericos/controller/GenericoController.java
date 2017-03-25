package com.gesLaboral.genericos.controller;

import java.io.Serializable;

import org.primefaces.event.SelectEvent;

import com.gesLaboral.genericos.service.GenericoService;
import com.gesLaboral.idioma.controller.IdiomaController;
import com.gesLaboral.maestros.empresas.controller.EmpresaController;
import com.gesLaboral.maestros.empresas.model.Empresa;
import com.gesLaboral.maestros.estadisticas.controller.EstadisticaController;
import com.gesLaboral.maestros.jornadas.controller.JornadaController;
import com.gesLaboral.maestros.trabajadores.controller.TrabajadorController;
import com.gesLaboral.maestros.usuarios.controller.UsuarioController;
import com.gesLaboral.maestros.usuarios.controllerImpl.LoginControllerImpl;
import com.gesLaboral.maestros.usuarios.model.Usuario;
import com.gesLaboral.navigation.controller.NavigationController;
import com.gesLaboral.session.controler.SessionController;

public interface GenericoController<T> extends Serializable {

	public GenericoService<T> getService();

	public void setService(GenericoService<T> genericoService);

	public Usuario getUsuarioSesion();

	public void setUsuarioSesion(Usuario usuarioSesion);

	public T getModel();

	public void setModel(T model);

	public String actionGoMod();

	public String actionGoDel();

	public String actionGoAdd();

	public String actionGoShow();


	public NavigationController getNavigationController();


	public SessionController getSessionController();

	public IdiomaController getIdiomaController();
	
	public TrabajadorController getTrabajadorController();
	
	public EmpresaController getEmpresasController();
	
	public JornadaController getJornadaController();
	
	public EstadisticaController getEstadisiticaTrabajadorController();
	
	public EstadisticaController getEstadisiticaEmpresaController();

	
	public Empresa getEmpresa();

	public String cambioEstado(String estado);

	public UsuarioController getUsuarioController();

	public String modModel();

	public String addModel(String codigo);

	public String delModel();

	public void onReturnDialog(SelectEvent event);

	public void newInstanceModel();

	public Class<T> getClasegenerica();

	public String cancelAbmAction();
	
	public String aplicarFiltros();
	
	public String delModelModal();
	
	public LoginControllerImpl getLoginController();
}

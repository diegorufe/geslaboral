package com.gesLaboral.navigation.controller;

import com.gesLaboral.genericos.controller.GenericoController;

public interface NavigationController extends GenericoController<Object> {

	/**
	 * Ir a empresa maestros
	 * 
	 * @return
	 */
	public String goEmpresas();

	/**
	 * Método para ir a la página de login
	 * 
	 * @return
	 */
	public String goLogin();

	public String goLoginJquery();

	public String goLoginUri();

	public String goHomeUri();

	public String redirectHome();

	public String redirectLogin();

	public String goCuentaUri();

	public String goEmpresaUri();

	public String goTrabajadorUri();
	
	public String goJornadasUri();
	
	public String goEstadisticasTrabajadorUri();
	
	public String goEstadisticasEmpresaUri();
	
	public String goEstadisticasUsuarioUri();
	
	public String goUsuariosUri();
	
	public String goAyudaUri();
	
	public String goMejorasUri();
	
	public String goAcercaUri();

}

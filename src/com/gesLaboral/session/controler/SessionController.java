package com.gesLaboral.session.controler;

import com.gesLaboral.genericos.controller.GenericoController;

public interface SessionController extends GenericoController<Object> {
	public String generateIdUriRequest();

	public void comprubaLoginListener();
	
	public boolean isAdminRol();
	
	
}

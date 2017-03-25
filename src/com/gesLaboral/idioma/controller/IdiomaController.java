package com.gesLaboral.idioma.controller;

import com.gesLaboral.genericos.controller.GenericoController;

public interface IdiomaController extends GenericoController<Object> {
	public void addMessageError(String error);
	public void addMessageInfo(String info);
	public String getTraducion(String msg);
}

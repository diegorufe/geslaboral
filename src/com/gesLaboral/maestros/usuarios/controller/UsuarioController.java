package com.gesLaboral.maestros.usuarios.controller;

import com.gesLaboral.genericos.controller.GenericoController;
import com.gesLaboral.maestros.usuarios.model.Usuario;

public interface UsuarioController extends GenericoController<Usuario> {
	public String registrarse();

	public String modificarCuenta();

	public String borrarCuenta();

	public void cambiarPasswordListener();
}

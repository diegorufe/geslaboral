package com.gesLaboral.maestros.usuarios.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import com.gesLaboral.genericos.controller.GenericoController;
import com.gesLaboral.maestros.usuarios.model.Usuario;

public interface LoginCotroller extends GenericoController<Usuario> {

	public String login() throws ServletException, IOException;

	public String logout();
}

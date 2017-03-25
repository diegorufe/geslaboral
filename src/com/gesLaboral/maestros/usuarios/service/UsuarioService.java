package com.gesLaboral.maestros.usuarios.service;

import com.gesLaboral.genericos.service.GenericoService;
import com.gesLaboral.maestros.usuarios.model.Usuario;

public interface UsuarioService extends GenericoService<Usuario> {
	public Usuario validaLogin(String nick, String password);

	/**
	 * Método para saber si el usuario es un usuario en la base de datos
	 * 
	 * @param usario
	 *            es el usuario a comprobar
	 * @return true si existe o false sino existe
	 */
	public boolean isUsuario(Usuario usario);
	
	/**
	 * Método para saber si el usuario es un usuario en la base de datos
	 * 
	 * @param usario
	 *            es el usuario a comprobar
	 * @return true si existe o false sino existe
	 */
	public boolean isUsuarioSinMd5(Usuario usario);

	/**
	 * Método para guardar un usuario en la base de datos
	 * 
	 * @param usuario
	 *            es el usuario a guardar en la base de datos
	 */
	public void save(Usuario usuario);

	/**
	 * Método para obtener un usuario por su nick
	 * 
	 * @param nick
	 *            es el nick del usuario
	 * @return el usuario del nick o null
	 */
	public Usuario getByNick(String nick);
}

package com.gesLaboral.maestros.usuarios.dao;

import com.gesLaboral.genericos.dao.GenericoDao;
import com.gesLaboral.maestros.usuarios.model.Usuario;

public interface UsuarioDao extends GenericoDao<Usuario> {
	/**
	 * Método para obtener el usuario mediante el nick y el password
	 * 
	 * @param nick
	 *            es el nick del usuario
	 * @param password
	 *            es el password de usuario
	 * @return el usuario si coincide con el nick y el password
	 */
	public Usuario getUser(String nick, String password);

	/**
	 * Método para validar si es un usuario de la base de datos
	 * 
	 * @param nick
	 *            es el nick del usuario
	 * @param id
	 *            es el id del usuario
	 * @return el usuario si coincide el nick
	 */
	public Usuario getUserByNick(String nick);
	
	public String getPasswordUser(String nick);

	public void save(Usuario usuario);
}

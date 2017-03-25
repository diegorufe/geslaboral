package com.gesLaboral.maestros.usuarios.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.gesLaboral.genericos.daoImpl.GenericoDaoImpl;
import com.gesLaboral.maestros.usuarios.dao.UsuarioDao;
import com.gesLaboral.maestros.usuarios.model.Usuario;

public class UsuarioDaoImpl extends GenericoDaoImpl<Usuario> implements UsuarioDao {
	/**
	 * Método para obtener el usuario mediante el nick y el password
	 * 
	 * @param nick
	 *            es el nick del usuario
	 * @param password
	 *            es el password de usuario
	 * @return el usuario si coincide con el nick y el password
	 */
	public Usuario getUser(String nick, String password) {

		Query query;
		Usuario usuario = null;

		if (password != null && nick != null) {
			EntityManager entityManager = getEntityManager();
			query = entityManager.createQuery("from Usuario u where u.nick = :nick and u.password = :password",
					Usuario.class);
			query.setParameter("nick", nick);
			query.setParameter("password", password);
			query.setMaxResults(1);
			try {
				usuario = (Usuario) query.getSingleResult();
			} catch (NoResultException ex) {
				usuario = null;
			}
		}

		return usuario;
	}

	/**
	 * Método para validar si es un usuario de la base de datos
	 * 
	 * @param nick
	 *            es el nick del usuario
	 * @param id
	 *            es el id del usuario
	 * @return el usuario si coincide el nick
	 */
	public Usuario getUserByNick(String nick) {
		Usuario usuario = null;
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery("from Usuario u where u.nick = :nick", Usuario.class);
		query.setParameter("nick", nick);
		query.setMaxResults(1);
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch (NoResultException ex) {
			usuario = null;
		}
		return usuario;
	}

	@Override
	public String getPasswordUser(String nick) {
		String password = null;
		EntityManager entityManager = getEntityManager();
		Query query = entityManager.createQuery("Select u.password from Usuario u where u.nick = :nick", String.class);
		query.setParameter("nick", nick);
		query.setMaxResults(1);
		try {
			password = (String) query.getSingleResult();
		} catch (NoResultException ex) {
			password = null;
		}
		return password;
	}
}

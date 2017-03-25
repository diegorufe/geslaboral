package com.gesLaboral.maestros.usuarios.serviceImpl;

import org.springframework.stereotype.Service;

import com.gesLaboral.genericos.serviceImpl.GenericoServiceImpl;
import com.gesLaboral.maestros.usuarios.dao.UsuarioDao;
import com.gesLaboral.maestros.usuarios.model.Usuario;
import com.gesLaboral.maestros.usuarios.service.UsuarioService;
import com.gesLaboral.util.Cifrado;

@Service
public class UsuarioServiceImpl extends GenericoServiceImpl<Usuario> implements UsuarioService {

	public UsuarioServiceImpl() {
	}

	@Override
	public Usuario validaLogin(String nick, String password) {
		return ((UsuarioDao) getDao()).getUser(nick, Cifrado.md5(password));
	}

	@Override
	public boolean isUsuario(Usuario usuario) {
		boolean existe = false;
		Usuario usuarioExiste = ((UsuarioDao) getDao()).getUser(usuario.getNick(), Cifrado.md5(usuario.getPassword()));
		if (usuarioExiste != null) {
			existe = true;
		}
		usuarioExiste = ((UsuarioDao) getDao()).getUserByNick(usuario.getNick());
		if (usuarioExiste != null) {
			existe = true;
		}
		return existe;
	}

	@Override
	public void save(Usuario usuario) {
		((UsuarioDao) getDao()).save(usuario);
	}

	@Override
	public Usuario getByNick(String nick) {
		return ((UsuarioDao) getDao()).getUserByNick(nick);
	}

	@Override
	public boolean isUsuarioSinMd5(Usuario usuario) {
		boolean existe = false;
		Usuario usuarioExiste = ((UsuarioDao) getDao()).getUser(usuario.getNick(), usuario.getPassword());
		if (usuarioExiste != null) {
			existe = true;
		}
		usuarioExiste = ((UsuarioDao) getDao()).getUserByNick(usuario.getNick());
		if (usuarioExiste != null) {
			existe = true;
		}
		return existe;
	}

}

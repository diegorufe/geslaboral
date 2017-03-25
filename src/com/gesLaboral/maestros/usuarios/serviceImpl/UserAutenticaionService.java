package com.gesLaboral.maestros.usuarios.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gesLaboral.maestros.usuarios.dao.RolDao;
import com.gesLaboral.maestros.usuarios.dao.UsuarioDao;
import com.gesLaboral.maestros.usuarios.model.Rol;
import com.gesLaboral.maestros.usuarios.model.Usuario;

@Service // Etiqueta que nos indica que es un servicio
public class UserAutenticaionService implements UserDetailsService {
	
	
	private UsuarioDao usuarioDao;// Nosostros no se lo pasamos Spring se
	 						// encarga de inyectarselo
	private RolDao rolDao;// Nosostros no se lo pasamos Spring se encarga de
							// inyectarselo

	@Override
	public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.getUserByNick(nick);
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setNick("Error");
			usuario.setPassword("Error");
			usuario.setRoles(new ArrayList<Rol>());
		}else{
//			if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
//				String password = usuarioDao.getPasswordUser(nick);
//				if (password != null) {
//					usuario.setPassword(password);
//				}
//			}
		}
		List<GrantedAuthority> authorities = buildUserAuthority(usuario.getRoles());
		return buildUserForAuthentication(usuario, authorities);
	}

	/**
	 * Metodo para construir un usuario validador por spring security
	 * 
	 * @param usuario
	 *            es el usuario a construir
	 * @param authorities
	 *            son los roles del usuario
	 * @return
	 */
	private User buildUserForAuthentication(Usuario usuario, List<GrantedAuthority> authorities) {
		return new User(usuario.getNick(), usuario.getPassword(), true, true, true, true, authorities);
	}

	/**
	 * Metodo para obtner la lista de autoridades del usuario
	 * 
	 * @param rol
	 *            es el rol del usuario
	 * @return una lista con las autoridades del usuario
	 */
	private List<GrantedAuthority> buildUserAuthority(List<Rol> roles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		// Build user's authorities
		for (Rol rol : roles) {
			setAuths.add(new SimpleGrantedAuthority(rol.getTipo()));
		}
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
		return result;
	}

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public RolDao getRolDao() {
		return rolDao;
	}

	public void setRolDao(RolDao rolDao) {
		this.rolDao = rolDao;
	}

}

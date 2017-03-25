package com.gesLaboral.maestros.usuarios.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gesLaboral.maestros.usuarios.constantes.UsuarioConstantes;
import com.gesLaboral.maestros.usuarios.enums.EnumPermisos;
import com.gesLaboral.maestros.usuarios.model.Rol;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4195028131109564576L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "nick", unique = true)
	private String nick;
	@Column(name = "password")
	private String password;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "usuarios", cascade = {CascadeType.PERSIST,  CascadeType.MERGE})
	private List<Rol> roles;
	@Transient
	private String passwordRepeat;
	@Transient
	private boolean cambiarPassword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> tiposUsuario) {
		this.roles = tiposUsuario;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public boolean isCambiarPassword() {
		return cambiarPassword;
	}

	public void setCambiarPassword(boolean cambiarPassword) {
		this.cambiarPassword = cambiarPassword;
	}

	/**
	 * Método para saber si el usuario es administrador
	 * 
	 * @return true si es administrador o false sino lo es
	 */
	public boolean isAdmin() {

		boolean isAdmin = false;
		if (roles != null && roles.size() > 0) { // Si tiene roles comprobamos
			for (Rol rol : roles) {
				String permiso = UsuarioConstantes.PERMISOS.get(rol.getTipo());
				if (permiso != null && permiso.equals(EnumPermisos.ADMIN.getValor())) {
					isAdmin = true;
					break;
				}
			}
		}
		return isAdmin;
	}

}

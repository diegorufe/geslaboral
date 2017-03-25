package com.gesLaboral.maestros.empresas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gesLaboral.maestros.usuarios.model.Usuario;

@Entity
@Table(name = "empresas")
public class Empresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6144796633962978759L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id; // Id unico
	@Column(name = "nombre")
	private String nombre; // Nombre empresa
	@Column(name = "dir1")
	private String dir1; // 1º Dirección
	@Column(name = "poblacion")
	private String poblacion;
	@Column(name = "provincia")
	private String provincia;
	@Column(name = "pais")
	private String pais;
	@Column(name = "codigo", unique = true)
	private String codigo; // Código de empresa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioCreacion")
	private Usuario usuarioCreacion; // Usuario de creación de la empresa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioModificacion")
	private Usuario usuarioModificacion; // Usuario de modificación de la
											// empresa

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDir1() {
		return dir1;
	}

	public void setDir1(String dir1) {
		this.dir1 = dir1;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Usuario getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(Usuario usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Usuario getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Usuario usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	@Override
	public String toString() {
		int idToS = id == null ? 0 : id;
		return String.valueOf(idToS);
	}

}

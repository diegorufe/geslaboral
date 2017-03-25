package com.gesLaboral.maestros.trabajadores.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.gesLaboral.maestros.empresas.model.Empresa;
import com.gesLaboral.maestros.usuarios.model.Usuario;

@Entity
@Table(name = "trabajadores")
public class Trabajador implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8761372679109787102L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "codigo", unique = true)
	private String codigo;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido1")
	private String apellido1;
	@Column(name = "apellido2")
	private String apellido2;
	@Column(name = "dir1")
	private String dir1;
	@Column(name = "poblacion")
	private String poblacion;
	@Column(name = "provincia")
	private String provincia;
	@Column(name = "pais")
	private String pais;
	@Column(name = "fechaNacimiento")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date fechaNacimiento;
	@Column(name = "fechaAlta")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date fechaAlta;
	@Column(name = "fechaBaja")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date fechaBaja;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioCreacion")
	private Usuario usuarioCreacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioModificacion")
	private Usuario usuarioModificacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresaId")
	private Empresa empresa;
	@Transient
	private String codigoEmpresa;

	public Trabajador() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getCodigoEmpresa() {
		if (codigoEmpresa == null) {
			if (getEmpresa() != null && getEmpresa().getCodigo() != null) {
				codigoEmpresa = getEmpresa().getCodigo();
			}
		}
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	@Override
	public String toString() {
		int idToS = id == null ? 0 : id;
		return String.valueOf(idToS);
	}

}

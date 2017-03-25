package com.gesLaboral.maestros.jornadas.model;

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

import com.gesLaboral.maestros.jornadas.constantes.EnumTipos;
import com.gesLaboral.maestros.trabajadores.model.Trabajador;

@Entity
@Table(name = "jornadas")
public class Jornada implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5893217754913459476L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "fechaInicio")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaInicio;

	@Column(name = "fechaFin")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaFin;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "descri")
	private String descri;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trabajadorId")
	private Trabajador trabajador;

	@Transient
	private String codigoTraba;

	@Transient
	private String nombreCompletoTraba;

	@Transient
	private EnumTipos tipoEnum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescri() {
		return descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public String getCodigoTraba() {
		return codigoTraba;
	}

	public void setCodigoTraba(String codigoTraba) {
		this.codigoTraba = codigoTraba;
	}

	public String getNombreCompletoTraba() {
		return nombreCompletoTraba;
	}

	public void setNombreCompletoTraba(String nombreCompletoTraba) {
		this.nombreCompletoTraba = nombreCompletoTraba;
	}

	public EnumTipos getTipoEnum() {
		EnumTipos tipoEnum = EnumTipos.convert(tipo);
		if (tipoEnum == null || tipoEnum == EnumTipos.UNDEFINED) {
			tipoEnum = EnumTipos.TRABAJO;
		}
		return tipoEnum;
	}

	public void setTipoEnum(EnumTipos tipoEnum) {
		if(tipoEnum != null){
			tipo = tipoEnum.getValue2();
		}
		this.tipoEnum = tipoEnum;
	}

	@Override
	public String toString() {
		int idToS = id == null ? 0 : id;
		return String.valueOf(idToS);
	}
}

package com.gesLaboral.maestros.usuarios.enums;

public enum EnumPermisos {
	UNDEFINED(""), ADMIN("A"), BASIC("B");

	private String valor;

	private EnumPermisos(String valor) {
		this.valor = valor;
	}

	/**
	 * Obtener el valor del Enum
	 * 
	 * @return
	 */
	public String getValor() {
		return valor;
	}
}

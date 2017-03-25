package com.gesLaboral.genericos.filtros;

public enum EnumTipoFiltro {
	UNDEFINED,
	IGUAL,
	MENOR,
	MENOR_IGUAL,
	MAYOR,
	MAYOR_IGUAL,
	LIKE_TODO,
	LIKE_PRINCIPIO,
	LIKE_FINAL,
	ENTRE,
	EN,
	NO_EN,
	Y,
	O,
	DISTINTO;
	
	private EnumTipoFiltro() {
	}
}

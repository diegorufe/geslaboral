package com.gesLaboral.maestros.jornadas.constantes;

public enum EnumTipos {

	UNDEFINED("", ""), ACCIDENTE("Acidente", "AC"), AUSENCIA("Ausencia", "AU"), ENFERMEDAD("Enfermedad", "EN"), FESTIVO(
			"Festivo", "FE"), HUELGA("Huelga", "HU"), MATERNIDAD("Maternidad", "MA"), PATERNIDAD("Paternidad",
					"PA"), PERMISO("Permiso", "PE"), TRABAJO("Trabajo", "TR"), VACACIONES("Vacaciones", "VA");

	private String value;
	private String value2;

	private EnumTipos(String value, String value2) {
		this.value = value;
		this.value2 = value2;
	}

	public String getValue() {
		return value;
	}

	public String getValue2() {
		return value2;
	}

	public static EnumTipos convert(String tipo) {
		EnumTipos value = EnumTipos.UNDEFINED;
		if (tipo != null) {
			EnumTipos[] values = EnumTipos.values();
			for (EnumTipos enumTipos : values) {
				if (enumTipos.getValue2().equals(tipo.trim())) {
					value = enumTipos;
					break;
				}
			}
		}
		return value;
	}

}

package com.gesLaboral.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.gesLaboral.maestros.jornadas.constantes.EnumTipos;

public class TipoJornadaConverter extends BaseConverter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		EnumTipos tipo = EnumTipos.convert(arg2);
		return tipo;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		EnumTipos tipo = EnumTipos.UNDEFINED;
		try {
			tipo = (EnumTipos) arg2;
		} catch (Exception e) {
			return "";
		}
		return tipo.getValue2();
	}

}

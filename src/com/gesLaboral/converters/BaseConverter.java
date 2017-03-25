package com.gesLaboral.converters;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.gesLaboral.idioma.controller.IdiomaController;
import com.gesLaboral.idioma.controllerImpl.IdiomaControllerImpl;

public abstract class BaseConverter implements Converter {
	
	
	/**
	 * Método para cojer el controlador de idiomas
	 * @return
	 */
	public IdiomaController getIdiomaController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		IdiomaController idiomaController = apli.evaluateExpressionGet(context, "#{idiomaController}",
				IdiomaController.class);
		if (idiomaController == null) {
			idiomaController = new IdiomaControllerImpl();
		}
		return idiomaController;
	}
	
}

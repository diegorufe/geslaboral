package com.gesLaboral.validators;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.gesLaboral.idioma.controller.IdiomaController;
import com.gesLaboral.idioma.controllerImpl.IdiomaControllerImpl;

public class BaseValidator {
	
	public BaseValidator() {
		
	}
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

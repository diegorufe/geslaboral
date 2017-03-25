package com.gesLaboral.validators;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * 
 * @author Usuario
 *
 */
@FacesValidator("DateValidator")
public class DateValidator extends BaseValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		try {
			if (value != null) {
				Date fecha = (Date) value;
			}
		} catch (Exception e) {
			UIInput input = (UIInput) component;
			input.setValue(null);
			FacesMessage msg = new FacesMessage(this.getIdiomaController().getTraducion("date.error"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}

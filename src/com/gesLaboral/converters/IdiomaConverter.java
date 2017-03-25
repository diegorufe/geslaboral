package com.gesLaboral.converters;
import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class IdiomaConverter extends BaseConverter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Locale locale = new Locale("es", "ES");
		if (arg2.trim().equalsIgnoreCase("inglés") || arg2.trim().equalsIgnoreCase("english")) {
			locale = new Locale("en", "EN");
		}
		return locale;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Locale locale = (Locale) arg2;
		return locale.getDisplayLanguage();
	}

}

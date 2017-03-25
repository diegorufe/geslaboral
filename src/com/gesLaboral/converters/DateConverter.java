package com.gesLaboral.converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class DateConverter extends BaseConverter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
		curFormater.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		Date date = null;
		try {
			date = (Date) curFormater.parse(arg2.trim());
			if (arg2 != null && !arg2.trim().isEmpty()) {
				String newDate = curFormater.format(date);
				if (!arg2.trim().equalsIgnoreCase(newDate.trim())) {
					date = null;
					getIdiomaController().addMessageError("date.error");
				}
			}

		} catch (Exception e) {
			date = null;
			getIdiomaController().addMessageError("date.error");
		}
		return date;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		String date = null;
		try {
			date = df.format(arg2);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}

}

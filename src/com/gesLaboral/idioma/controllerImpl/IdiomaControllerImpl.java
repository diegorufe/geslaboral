package com.gesLaboral.idioma.controllerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.idioma.controller.IdiomaController;

public class IdiomaControllerImpl extends GenericoControllerImpl<Object> implements IdiomaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6326202567058732398L;

	private List<Locale> supportedLocales;
	private Locale locale;

	public IdiomaControllerImpl() {
		supportedLocales = new ArrayList<Locale>();
		//locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
		supportedLocales.add(new Locale("es", "ES"));
		supportedLocales.add(new Locale("en", "EN"));
		locale = new Locale("es", "ES");
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		this.locale = locale;
		RequestContext.getCurrentInstance().execute("document.title='"+getTraducion("titulo.login")+"'");
		RequestContext.getCurrentInstance().execute("changueInnerForAtrribute('href','#tabs:tab1','"+getTraducion("titulo.login")+"');");
		RequestContext.getCurrentInstance().execute("changueInnerForAtrribute('href','#tabs:tab2','"+getTraducion("login.registrarse")+"');");
//		RequestContext.getCurrentInstance().execute("changueInnerForAtrribute('for','tabs:login:nickDiv:j_username','"+getTraducion("usuario.nick")+"');");
//		RequestContext.getCurrentInstance().execute("changueInnerForAtrribute('for','tabs:login:passwordDiv:j_password','"+getTraducion("usuario.password")+"');");
//		RequestContext.getCurrentInstance().execute("changueInnerForAtrribute('for','tabs:registrarse:nickDiv:nickInput','"+getTraducion("usuario.nick")+"');");
//		RequestContext.getCurrentInstance().execute("changueInnerForAtrribute('for','tabs:registrarse:passwordDiv:passwordInput','"+getTraducion("usuario.password")+"');");
//		RequestContext.getCurrentInstance().execute("changueInnerForAtrributeAll('value','español','"+getTraducion("idioma.español")+"');");
//		RequestContext.getCurrentInstance().execute("changueInnerForAtrributeAll('value','spanish','"+getTraducion("idioma.español")+"');");
//		RequestContext.getCurrentInstance().execute("changueInnerForAtrributeAll('value','inglés','"+getTraducion("idioma.inglés")+"');");
//		RequestContext.getCurrentInstance().execute("changueInnerForAtrributeAll('value','english','"+getTraducion("idioma.inglés")+"');");
	}

	public List<Locale> getSupportedLocales() {
		return supportedLocales;
	}

	@Override
	public void addMessageError(String error) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString(error), null);
		context.addMessage(null, message);
	}

	@Override
	public void addMessageInfo(String info) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString(info), null);
		context.addMessage(null, message);
	}

	@Override
	public String getTraducion(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		return bundle.getString(msg);
	}

}

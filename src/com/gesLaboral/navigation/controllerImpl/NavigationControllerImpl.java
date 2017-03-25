package com.gesLaboral.navigation.controllerImpl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.gesLaboral.genericos.constantes.Constantes;
import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.maestros.usuarios.model.Usuario;
import com.gesLaboral.navigation.constantes.UrlsNavigation;
import com.gesLaboral.navigation.controller.NavigationController;

public class NavigationControllerImpl extends GenericoControllerImpl<Object> implements NavigationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8507520809591720663L;

	@Override
	public String goEmpresas() {
		String accion = Constantes.ERROR;
		Usuario usuario = getUsuarioSesion();
		if (usuario != null) {
			if (!usuario.isAdmin()) {
				accion = Constantes.EXITO;
			}
		}
		return accion;
	}

	@Override
	public String goLogin() {
		String accion = UrlsNavigation.GO_LOGIN;
		return accion;
	}

	@Override
	public String goLoginUri() {
		String accion = UrlsNavigation.GO_LOGIN_URI;
		if(FacesContext.getCurrentInstance().getExternalContext().getSessionId(false) == null || FacesContext.getCurrentInstance().getExternalContext().getSessionId(false).trim().isEmpty()){
			FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		}
		RequestContext.getCurrentInstance().execute("document.title='"+getIdiomaController().getTraducion("titulo.login")+"'");
		return accion;
	}

	@Override
	public String goHomeUri() {
		String accion = UrlsNavigation.GO_HOME_URI;
		return accion;
	}

	@Override
	public String goLoginJquery() {
		String accion = UrlsNavigation.GO_LOGIN_JQUERY;
		return accion;
	}

	@Override
	public String redirectHome() {
		String accion = UrlsNavigation.REDIRECT_HOME;
		return accion;
	}

	@Override
	public String redirectLogin() {
		String accion = UrlsNavigation.REDIRECT_LOGIN;
		return accion;
	}

	@Override
	public String goCuentaUri() {
		// String accion = UrlsNavigation.GO_ACCOUNT_URI;
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", true);
		options.put("dynamic", true);
		options.put("width", 275);
		options.put("height", 350);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog(UrlsNavigation.DIALOG_CUENTA, options, null);
		return null;
	}

	@Override
	public String goEmpresaUri() {
		String accion = UrlsNavigation.GO_ENTERPRISE_URI;
		return accion;
	}

	@Override
	public String goTrabajadorUri() {
		String accion = UrlsNavigation.GO_TRABAJADORES_URI;
		return accion;
	}

	@Override
	public String goJornadasUri() {
		String accion = UrlsNavigation.GO_JORNADAS_URI;
		return accion;
	}

	@Override
	public String goEstadisticasTrabajadorUri() {
		String accion = UrlsNavigation.GO_ESTADISTICAS_TRABAJADOR_URI;
		return accion;
	}
	
	@Override
	public String goEstadisticasEmpresaUri() {
		String accion = UrlsNavigation.GO_ESTADISTICAS_EMPRESA_URI;
		return accion;
	}

	@Override
	public String goEstadisticasUsuarioUri() {
		String accion = UrlsNavigation.GO_ESTADISTICAS_USUARIO_URI;
		return accion;
	}

	@Override
	public String goUsuariosUri() {
		String accion = UrlsNavigation.GO_USUARIOS_URI;
		return accion;
	}

	@Override
	public String goAyudaUri() {
		String accion = UrlsNavigation.GO_AYUDAS_URI;
		return accion;
	}

	@Override
	public String goMejorasUri() {
		String accion = UrlsNavigation.GO_MEJORAS_URI;
		return accion;
	}

	@Override
	public String goAcercaUri() {
		String accion = UrlsNavigation.GO_ACERCA_URI;
		return accion;
	}
	

}

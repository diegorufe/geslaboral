package com.gesLaboral.session.controllerImpl;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;

import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.maestros.empresas.model.Empresa;
import com.gesLaboral.maestros.usuarios.enums.EnumPermisos;
import com.gesLaboral.maestros.usuarios.model.Rol;
import com.gesLaboral.maestros.usuarios.model.Usuario;
import com.gesLaboral.maestros.usuarios.serviceImpl.UsuarioServiceImpl;
import com.gesLaboral.session.controler.SessionController;

public class SessionControllerImpl extends GenericoControllerImpl<Object> implements SessionController {

	private Empresa empresaSeleccionada;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1743723645237849826L;

	@Override
	public String generateIdUriRequest() {
		return String.valueOf(UUID.randomUUID());
	}

	public Empresa getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}

	public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
		this.empresaSeleccionada = empresaSeleccionada;
	}

	public void comprubaLoginListener() {
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (e instanceof BadCredentialsException) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			getIdiomaController().addMessageError("login.error");
		} else {
			Principal user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
			if (user != null && (getUsuarioController() == null || getUsuarioSesion() == null
					|| getUsuarioSesion().getNick() == null || getUsuarioSesion().getNick().trim().isEmpty())) {
				Usuario usuario = ((UsuarioServiceImpl) getUsuarioController().getService()).getByNick(user.getName());
				getSessionController().setUsuarioSesion(usuario);
			}

		}
	}

	@Override
	public boolean isAdminRol() {
		comprubaLoginListener();
		boolean isAdmin = false;
		if (getUsuarioSesion() != null) {
			if (getUsuarioSesion().getRoles() != null && getUsuarioSesion().getRoles().size() > 0) {
				for (Rol rol : getUsuarioSesion().getRoles()) {
					if (rol.getTipo().equalsIgnoreCase(EnumPermisos.ADMIN.getValor())) {
						isAdmin = true;
						break;
					}
				}
			}
		}
		return isAdmin;
	}

	@SuppressWarnings("static-access")
	@Override
	public void onReturnDialog(SelectEvent event) {
		if (event != null && event != null && event.getObject() != null && event.getObject() instanceof Boolean) {
			if ((Boolean) event.getObject()) {
				 FacesContext facesContext = FacesContext.getCurrentInstance();
				 HttpSession httpSession = (HttpSession)
				 facesContext.getExternalContext().getSession(false);
				 if (httpSession != null) {
				 httpSession.invalidate();
				 }
				
				 FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				 ExternalContext ec =
				 FacesContext.getCurrentInstance().getExternalContext();
				RequestContext.getCurrentInstance().releaseThreadLocalCache();
				 try {
				 ec.redirect(ec.getRequestContextPath() + "/");
				 } catch (IOException e) {
				 e.printStackTrace();
				 }
				 SecurityContextHolder.getContext().setAuthentication(null);
				 SecurityContextHolder.getContext();
			}
		}
	}

}

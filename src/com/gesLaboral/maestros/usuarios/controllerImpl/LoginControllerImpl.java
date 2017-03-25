package com.gesLaboral.maestros.usuarios.controllerImpl;

import java.io.IOException;
import java.security.Principal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;

import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.maestros.usuarios.controller.LoginCotroller;
import com.gesLaboral.maestros.usuarios.model.Usuario;
import com.gesLaboral.maestros.usuarios.service.UsuarioService;
import com.gesLaboral.maestros.usuarios.serviceImpl.UsuarioServiceImpl;

public class LoginControllerImpl extends GenericoControllerImpl<Usuario> implements LoginCotroller {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3386976327899957205L;
	private boolean closeSession;

	public LoginControllerImpl() {
		super();
		setUsuarioSesion(null);
		setModel(new Usuario());
	}

	@Override
	public String login() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext context = facesContext.getExternalContext();
		try {
//			context.dispatch("/j_spring_security_check?j_username=" + ((Usuario) getModel()).getNick() + "&j_password="
//					+ ((Usuario) getModel()).getPassword());
			context.dispatch("/j_spring_security_check");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Principal user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (user != null) {
			// Usuario usuario = ((UsuarioServiceImpl)
			// getService()).getByNick(user.getName());
			UsuarioService usuarioService = (UsuarioService) getUsuarioController().getService();
			Usuario usuario = usuarioService.getByNick(user.getName());
			getSessionController().setUsuarioSesion(usuario);
		}
		FacesContext.getCurrentInstance().responseComplete();
		return null;
	}

	public void comprubaLoginListener() {
		if(FacesContext.getCurrentInstance().getExternalContext().getSession(false) == null || FacesContext.getCurrentInstance().getExternalContext().getSessionId(false) == null || FacesContext.getCurrentInstance().getExternalContext().getSessionId(false).trim().isEmpty()){
			FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			RequestContext.getCurrentInstance().update("messagesLoginG");
		}
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (e instanceof BadCredentialsException) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			getIdiomaController().addMessageError("login.error");
			e.printStackTrace();
		} else {
			Principal user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
			if (user != null) {
				Usuario usuario = ((UsuarioServiceImpl) getService()).getByNick(user.getName());
				getSessionController().setUsuarioSesion(usuario);
			}

		}
		if (closeSession) {
			getIdiomaController().addMessageInfo("session.close");
			RequestContext.getCurrentInstance().update("messagesLoginG");
			closeSession = false;
		}

	}

	@SuppressWarnings("static-access")
	@Override
	public String logout() {
		Cookie[] cookies = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				cookie.setValue(null);
				cookie.setPath("/");
				((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse())
						.addCookie(cookie);
			}
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext context = facesContext.getExternalContext();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
		if (httpSession != null) {
			httpSession.invalidate();
		}
		try {
			context.dispatch("/j_spring_security_logout");
		} catch (IOException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().responseComplete();

		// HttpServletRequest request = (HttpServletRequest)
		// context.getRequest();
		// Authentication auth =
		// SecurityContextHolder.getContext().getAuthentication();
		// if (auth != null) {
		// new SecurityContextLogoutHandler().logout(
		// (HttpServletRequest)
		// FacesContext.getCurrentInstance().getExternalContext().getRequest(),
		// (HttpServletResponse)
		// FacesContext.getCurrentInstance().getExternalContext().getResponse(),
		// auth);
		// }

		return null;
		// FacesContext facesContext = FacesContext.getCurrentInstance();
		// HttpSession httpSession = (HttpSession)
		// facesContext.getExternalContext().getSession(false);
		// if (httpSession != null) {
		// httpSession.invalidate();
		// }
		//
		// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		// ExternalContext ec =
		// FacesContext.getCurrentInstance().getExternalContext();
		// RequestContext.getCurrentInstance().releaseThreadLocalCache();
		// try {
		// ec.redirect(ec.getRequestContextPath() + "/");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// SecurityContextHolder.getContext().setAuthentication(null);
		// SecurityContextHolder.getContext();
		// closeSession = true;
		// return null;
	}

	public boolean isCloseSession() {
		return closeSession;
	}

	public void setCloseSession(boolean closeSession) {
		this.closeSession = closeSession;
	}
}

package com.gesLaboral.maestros.usuarios.controllerImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.genericos.filtros.EnumTipoFiltro;
import com.gesLaboral.genericos.filtros.Filtro;
import com.gesLaboral.genericos.lazyTable.GenericoLazyTable;
import com.gesLaboral.maestros.usuarios.controller.UsuarioController;
import com.gesLaboral.maestros.usuarios.enums.EnumPermisos;
import com.gesLaboral.maestros.usuarios.model.Rol;
import com.gesLaboral.maestros.usuarios.model.Usuario;
import com.gesLaboral.maestros.usuarios.service.RolService;
import com.gesLaboral.maestros.usuarios.service.UsuarioService;
import com.gesLaboral.navigation.constantes.UrlsNavigation;
import com.gesLaboral.util.Cifrado;

public class UsuarioControllerImpl extends GenericoControllerImpl<Usuario> implements UsuarioController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7388198323769601881L;
	private RolService rolService;
	private Usuario cuenta;

	public UsuarioControllerImpl() {
		super();
		setModel(new Usuario()); // Fijamos model para interactural con el en
									// mantenimientos
		cuenta = new Usuario();
		if (getSessionController().getUsuarioSesion() != null
				&& !getSessionController().getUsuarioSesion().getNick().trim().isEmpty()) {
			cuenta = getSessionController().getUsuarioSesion();
			cuenta.setPassword("");
			cuenta.setPasswordRepeat("");
		}
		setShow(true);
		setModi(false);
	}

	@PostConstruct
	public void init() {
		if (getSessionController().getUsuarioSesion() != null
				&& !getSessionController().getUsuarioSesion().getNick().trim().isEmpty()) {
			Filtro usuarioFiltro = new Filtro();
			usuarioFiltro.setTipoFiltro(EnumTipoFiltro.DISTINTO);
			usuarioFiltro.setValue(getSessionController().getUsuarioSesion().getNick().trim());
			usuarioFiltro.setCampo("nick");
			LinkedList<Filtro> filtros = new LinkedList<Filtro>();
			filtros.add(usuarioFiltro);
			getService().setDefaultFiltros(filtros);
		}
		GenericoLazyTable<Usuario> lazyTable = getLazyTable();
		lazyTable.setGenericoService(getService());
		String[] columns = { "nick" };
		String[] columnsSorters = { "nick" };
		String[] columnsText = { "usuario.nick" };
		lazyTable.setNumColums(columns.length);
		lazyTable.generaColumsn(columns, columnsSorters, columnsText);
		lazyTable.setHeader("usuario.header");
	}

	@Override
	public String registrarse() {
		String url = UrlsNavigation.GO_REGISTARSE_ERROR;
		if (getModel() == null || getModel().getNick() == null || getModel().getNick().trim().isEmpty()) {
			getIdiomaController().addMessageError("usuario.nickNecesario");
			return url;
		}
		if (getModel() == null || getModel().getPassword() == null || getModel().getPassword().trim().isEmpty()) {
			getIdiomaController().addMessageError("usuario.passwordNecesario");
			return url;
		}
		if (((UsuarioService) getService()).isUsuario(getModel()) || ((UsuarioService) getService()).isUsuarioSinMd5(getModel())) {
			getIdiomaController().addMessageError("registrarse.existe");
		} else {
			String password = getModel().getPassword();
			getModel().setRoles(null);
			getModel().setPassword(Cifrado.md5(getModel().getPassword()));
			((UsuarioService) getService()).save(getModel());
			setModel(((UsuarioService) getService()).validaLogin(getModel().getNick(), password));
			List<Usuario> usuarios = new ArrayList<Usuario>();
			usuarios.add(getModel());
			List<Rol> roles = rolService.getBySql(
					"FROM " + Rol.class.getName() + " e where e.tipo = '" + EnumPermisos.BASIC.getValor() + "'");
			roles.forEach(rol -> rol.setUsuarios(usuarios));
			getModel().setRoles(roles);
			((UsuarioService) getService()).getDao().update(getModel());
			setModel(new Usuario());
			url = UrlsNavigation.GO_REGISTARSE_SUCCES;
		}
		return url;
	}

	public RolService getRolService() {
		return rolService;
	}

	public void setRolService(RolService rolService) {
		this.rolService = rolService;
	}

	public Usuario getCuenta() {
		return cuenta;
	}

	public void setCuenta(Usuario cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public String modificarCuenta() {
		if (cuenta == null || cuenta.getNick() == null || cuenta.getNick().trim().isEmpty()) {
			getIdiomaController().addMessageError("usuario.nickNecesario");
			return null;
		}
		if (cuenta == null || (cuenta.isCambiarPassword()
				&& (cuenta.getPassword() == null || cuenta.getPassword().trim().isEmpty()))) {
			getIdiomaController().addMessageError("usuario.passwordNecesario");
			return null;
		}
		Usuario usuario = ((UsuarioService) getService()).getByNick(cuenta.getNick());
		if (usuario != null && usuario.getId() != cuenta.getId()) {
			getIdiomaController().addMessageError("cuenta.existe");
		} else {
			if (cuenta.isCambiarPassword()) {
				if (!cuenta.getPassword().trim().equals(cuenta.getPasswordRepeat().trim())) {
					getIdiomaController().addMessageError("password.noIguales");
				} else {
					Usuario usuarioMod = cuenta;
					usuarioMod.setPassword(Cifrado.md5(usuarioMod.getPassword()));
					((UsuarioService) getService()).update(usuarioMod);
					setShow(true);
					setModi(false);
					getSessionController().setUsuarioSesion(usuarioMod);
					cuenta.setCambiarPassword(false);
					cuenta.setPasswordRepeat("");
					cuenta.setPassword("");
				}
			} else {
				Usuario usuarioMod = cuenta;
				usuarioMod.setPassword(getSessionController().getUsuarioSesion().getPassword());
				usuarioMod.setPassword(getSessionController().getUsuarioSesion().getPassword());
				((UsuarioService) getService()).update(usuarioMod);
				setShow(true);
				setModi(false);
				cuenta.setCambiarPassword(false);
				getIdiomaController().addMessageInfo("cuenta.mdoficada");
				getSessionController().setUsuarioSesion(usuarioMod);
				cuenta.setPasswordRepeat("");
				cuenta.setPassword("");
			}
		}
		
		return null;
	}

	@Override
	public String borrarCuenta() {
		if (cuenta == null || cuenta.getId() == null) {
			getIdiomaController().addMessageInfo("error.borrar");
			return null;
		} else {
			setModel(cuenta);
			if (cuenta.getNick().equalsIgnoreCase(getSessionController().getUsuarioSesion().getNick())) {
				super.delModel();
				RequestContext.getCurrentInstance().closeDialog(true);
				return null;

			}
			getIdiomaController().addMessageInfo("borrado.exito");
		}
		return super.delModel();
	}

	@Override
	public void cambiarPasswordListener() {
		// cuenta.setPassword("");
		cuenta.setPasswordRepeat("");
		if (getModel() != null) {
			getModel().setPasswordRepeat("");
		}
	}

	@Override
	public String actionGoAdd() {
		super.actionGoAdd();
		getModel().setCambiarPassword(true);
		RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMUSUARIO + "').show()");
		RequestContext.getCurrentInstance().update("account");
		return null;
	}

	@Override
	public String actionGoMod() {
		if (getLazyTable() != null && getLazyTable().getSelection() != null
				&& getLazyTable().getSelection().getNick() != null
				&& !getLazyTable().getSelection().getNick().trim().isEmpty()) {
			super.actionGoMod();
			getModel().setPasswordRepeat("");
			getModel().setCambiarPassword(false);
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMUSUARIO + "').show()");
			RequestContext.getCurrentInstance().update("account");
		} else {
			getIdiomaController().addMessageError("seleciona.registro");
		}
		return null;
	}

	@Override
	public String actionGoShow() {
		if (getLazyTable() != null && getLazyTable().getSelection() != null
				&& getLazyTable().getSelection().getNick() != null
				&& !getLazyTable().getSelection().getNick().trim().isEmpty()) {
			super.actionGoShow();
			getModel().setCambiarPassword(false);
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMUSUARIO + "').show()");
			RequestContext.getCurrentInstance().update("account");
		} else {
			getIdiomaController().addMessageError("seleciona.registro");
		}
		return null;
	}

	@Override
	public String actionGoDel() {
		if (getLazyTable() != null && getLazyTable().getSelection() != null
				&& getLazyTable().getSelection().getNick() != null
				&& !getLazyTable().getSelection().getNick().trim().isEmpty()) {
			super.actionGoDel();
			RequestContext.getCurrentInstance().execute("PF('delUsuario').show()");
		} else {
			getIdiomaController().addMessageError("seleciona.registro");
		}
		return null;
	}

	@Override
	public String addModel(String codigo) {
		getModel().setPassword(getModel().getPasswordRepeat());
		String registrarse = registrarse();
		if (registrarse.equalsIgnoreCase(UrlsNavigation.GO_REGISTARSE_SUCCES)) {
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMUSUARIO + "').hide()");
			RequestContext.getCurrentInstance().update("account");
		}
		return null;
	}

	@Override
	public String modModel() {
		if (getModel() == null || getModel().getNick() == null || getModel().getNick().trim().isEmpty()) {
			getIdiomaController().addMessageError("usuario.nickNecesario");
			return null;
		}
		if (getModel() == null || getModel().getPassword() == null || getModel().getPassword().trim().isEmpty()) {
			getIdiomaController().addMessageError("usuario.passwordNecesario");
			return null;
		}
		Usuario usuario = ((UsuarioService) getService()).getByNick(getModel().getNick());
		if (usuario != null && usuario.getId() != getModel().getId()) {
			getIdiomaController().addMessageError("cuenta.existe");
		} else {
			if (getModel().isCambiarPassword()) {
				getModel().setPassword(Cifrado.md5(getModel().getPasswordRepeat()));
				getModel().setPasswordRepeat(getModel().getPassword());
			} else {
				getModel().setPassword(getModel().getPassword());
			}
			((UsuarioService) getService()).update(getModel());
			setShow(true);
			setModi(false);
			getIdiomaController().addMessageInfo("cuenta.mdoficada");
			getModel().setCambiarPassword(false);
			getModel().setPasswordRepeat("");
			cambioEstado("S");
			// RequestContext.getCurrentInstance().update("account");
		}
		return null;
	}

	@Override
	public String cancelAbmAction() {
		if (isAdd()) {
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMUSUARIO + "').hide()");
		}
		return super.cancelAbmAction();
	}

	@Override
	public String delModel() {
		if (getModel() == null || getModel().getId() == null) {
			getIdiomaController().addMessageInfo("error.borrar");
			return null;
		} else {
			if (getModel().getNick().equalsIgnoreCase(getSessionController().getUsuarioSesion().getNick())) {
				super.delModel();
				return getLoginController().logout();

			}
			getIdiomaController().addMessageInfo("borrado.exito");
		}
		return super.delModel();
	}

	@Override
	public String delModelModal() {
		if (getModel() == null || getModel().getId() == null) {
			getIdiomaController().addMessageInfo("error.borrar");
			return null;
		} else {
			if (getModel().getNick().equalsIgnoreCase(getSessionController().getUsuarioSesion().getNick())) {
				super.delModel();
				return getLoginController().logout();

			}
			getIdiomaController().addMessageInfo("borrado.exito");
		}
		super.delModel();
		RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMUSUARIO + "').hide()");
		return null;
	}
	
	@Override
	public String cambioEstado(String estado) {
		super.cambioEstado(estado);
		if(estado.equalsIgnoreCase("S")){
			cuenta.setPassword("");
			cuenta.setPasswordRepeat("");
			cuenta.setCambiarPassword(false);
			if(getModel() != null){
				getModel().setPasswordRepeat("");
				getModel().setCambiarPassword(false);
			}
		}
		return null;
	}

}

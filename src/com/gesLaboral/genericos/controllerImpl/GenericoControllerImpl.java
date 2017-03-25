package com.gesLaboral.genericos.controllerImpl;

import java.lang.reflect.ParameterizedType;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import com.gesLaboral.genericos.controller.GenericoController;
import com.gesLaboral.genericos.lazyTable.GenericoLazyTable;
import com.gesLaboral.genericos.service.GenericoService;
import com.gesLaboral.idioma.controller.IdiomaController;
import com.gesLaboral.idioma.controllerImpl.IdiomaControllerImpl;
import com.gesLaboral.maestros.empresas.controller.EmpresaController;
import com.gesLaboral.maestros.empresas.controllerImpl.EmpresaControllerImpl;
import com.gesLaboral.maestros.empresas.model.Empresa;
import com.gesLaboral.maestros.estadisticas.controller.EstadisticaController;
import com.gesLaboral.maestros.estadisticas.controllerImpl.EstadisiticaControllerImpl;
import com.gesLaboral.maestros.jornadas.controller.JornadaController;
import com.gesLaboral.maestros.jornadas.controllerImpl.JornadaControllerImpl;
import com.gesLaboral.maestros.trabajadores.controller.TrabajadorController;
import com.gesLaboral.maestros.trabajadores.controllerImpl.TrabajadorControllerImpl;
import com.gesLaboral.maestros.usuarios.controller.UsuarioController;
import com.gesLaboral.maestros.usuarios.controllerImpl.LoginControllerImpl;
import com.gesLaboral.maestros.usuarios.controllerImpl.UsuarioControllerImpl;
import com.gesLaboral.maestros.usuarios.model.Usuario;
import com.gesLaboral.navigation.controller.NavigationController;
import com.gesLaboral.navigation.controllerImpl.NavigationControllerImpl;
import com.gesLaboral.session.controler.SessionController;
import com.gesLaboral.session.controllerImpl.SessionControllerImpl;

public class GenericoControllerImpl<T> implements GenericoController<T> {

	private boolean isModi;
	private boolean isDel;
	private boolean isAdd;
	private boolean isShow;
	private Usuario usuarioSesion;
	private T model;
	private static final long serialVersionUID = 1L;
	private GenericoService<T> service;
	private GenericoLazyTable<T> lazyTable;
	

	public GenericoControllerImpl() {
		lazyTable = new GenericoLazyTable<T>();
	}

	@Override
	public String cambioEstado(String estado) {
		if (estado != null) {
			if (estado.equalsIgnoreCase("M")) {
				isModi = true;
				isShow = false;
				isDel = false;
				isAdd = false;
			} else if (estado.equalsIgnoreCase("S")) {
				isModi = false;
				isShow = true;
				isDel = false;
				isAdd = false;
			} else if (estado.equalsIgnoreCase("D")) {
				isModi = false;
				isShow = false;
				isDel = true;
				isAdd = false;
			} else if (estado.equalsIgnoreCase("A")) {
				isModi = false;
				isShow = false;
				isDel = false;
				isAdd = true;
			} else {
				isModi = false;
				isShow = true;
				isDel = false;
				isAdd = false;
			}
		}
		return null;
	}

	@Override
	public NavigationController getNavigationController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		NavigationController navigationController = apli.evaluateExpressionGet(context, "#{navigationController}",
				NavigationController.class);
		if (navigationController == null) {
			navigationController = new NavigationControllerImpl();
		}
		return navigationController;
	}

	@Override
	public SessionController getSessionController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		SessionController sessionController = apli.evaluateExpressionGet(context, "#{sessionController}",
				SessionController.class);
		if (sessionController == null) {
			sessionController = new SessionControllerImpl();
		}
		return sessionController;
	}

	@Override
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

	@Override
	public GenericoService<T> getService() {
		return service;
	}

	@Override
	public void setService(GenericoService<T> service) {
		this.service = service;

	}

	@Override
	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}

	@Override
	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}

	@Override
	public T getModel() {
		return this.model;
	}

	@Override
	public void setModel(T model) {
		this.model = model;
	}

	@Override
	public UsuarioController getUsuarioController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		UsuarioController controller = apli.evaluateExpressionGet(context, "#{usuarioController}",
				UsuarioController.class);
		if (controller == null) {
			controller = new UsuarioControllerImpl();
		}
		return controller;
	}

	@Override
	public Empresa getEmpresa() {
//		SessionController sessionController = getSessionController();
//		return sessionController.getEmpresa();
		return null;
	}

	public boolean isModi() {
		return isModi;
	}

	public void setModi(boolean isModi) {
		this.isModi = isModi;
	}

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public GenericoLazyTable<T> getLazyTable() {
		return lazyTable;
	}

	public void setLazyTable(GenericoLazyTable<T> lazyTable) {
		this.lazyTable = lazyTable;
	}

	@Override
	public String modModel() {
		if (getModel() != null) {
			getService().update(model);
			cambioEstado("S");
		}
		return null;
	}

	@Override
	public String addModel(String codigo) {
		if (getModel() != null) {
			if (codigo != null && !codigo.trim().isEmpty()) {
				if (getService().getByCodigo(codigo, null) == null) {
					getService().save(model);
					cambioEstado("S");
				} else {
					getIdiomaController().addMessageError("codigo.existente");
				}
			} else {
				getIdiomaController().addMessageError("codigo.necesario");
			}
		}
		return null;
	}

	@Override
	public String delModel() {
		if (getModel() != null) {
			getService().delete(getModel());
			newInstanceModel();
		}
		return null;
	}

	@Override
	public String actionGoMod() {
		cambioEstado("M");
		if (getLazyTable() != null && getLazyTable().getSelection() != null) {
			setModel(getLazyTable().getSelection());
		}
		return null;
	}

	@Override
	public String actionGoDel() {
		cambioEstado("D");
		if (getLazyTable() != null && getLazyTable().getSelection() != null) {
			setModel(getLazyTable().getSelection());
		}
		return null;
	}

	@Override
	public String actionGoAdd() {
		cambioEstado("A");
		newInstanceModel();
		return null;
	}

	@Override
	public String actionGoShow() {
		cambioEstado("S");
		if (getLazyTable() != null && getLazyTable().getSelection() != null) {
			setModel(getLazyTable().getSelection());
		}
		return null;
	}

	@Override
	public void onReturnDialog(SelectEvent event) {
		
	}

	@Override
	public void newInstanceModel() {
		try {
			setModel(getClasegenerica().newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getClasegenerica() {
		ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) thisType.getActualTypeArguments()[0];
	}

	@Override
	public String cancelAbmAction() {
		cambioEstado("S");
		return null;
	}

	@Override
	public TrabajadorController getTrabajadorController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		TrabajadorController controller = apli.evaluateExpressionGet(context, "#{trabajadorController}",
				TrabajadorController.class);
		if (controller == null) {
			controller = new TrabajadorControllerImpl();
		}
		return controller;
	}

	@Override
	public EmpresaController getEmpresasController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		EmpresaController controller = apli.evaluateExpressionGet(context, "#{empresaController}",
				EmpresaController.class);
		if (controller == null) {
			controller = new EmpresaControllerImpl();
		}
		return controller;
	}

	@Override
	public JornadaController getJornadaController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		JornadaController controller = apli.evaluateExpressionGet(context, "#{jornadaController}",
				JornadaController.class);
		if (controller == null) {
			controller = new JornadaControllerImpl();
		}
		return controller;
	}

	@Override
	public String aplicarFiltros() {
		return null;
	}

	@Override
	public EstadisticaController getEstadisiticaTrabajadorController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		EstadisticaController controller = apli.evaluateExpressionGet(context, "#{estadisticaTrabajadorController}",
				EstadisticaController.class);
		if (controller == null) {
			controller = new EstadisiticaControllerImpl();
		}
		return controller;
	}

	@Override
	public EstadisticaController getEstadisiticaEmpresaController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		EstadisticaController controller = apli.evaluateExpressionGet(context, "#{estadisticaEmpresaController}",
				EstadisticaController.class);
		if (controller == null) {
			controller = new EstadisiticaControllerImpl();
		}
		return controller;
	}

	@Override
	public String delModelModal() {
		return null;
	}

	@Override
	public LoginControllerImpl getLoginController() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application apli = context.getApplication();
		LoginControllerImpl controller = apli.evaluateExpressionGet(context, "#{loginController}",
				LoginControllerImpl.class);
		if (controller == null) {
			controller = new LoginControllerImpl();
		}
		return controller;
	}
	
	
}

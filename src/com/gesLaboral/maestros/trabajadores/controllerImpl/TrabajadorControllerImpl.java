package com.gesLaboral.maestros.trabajadores.controllerImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;

import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.genericos.filtros.EnumTipoFiltro;
import com.gesLaboral.genericos.filtros.Filtro;
import com.gesLaboral.genericos.lazyTable.GenericoLazyTable;
import com.gesLaboral.maestros.empresas.model.Empresa;
import com.gesLaboral.maestros.trabajadores.controller.TrabajadorController;
import com.gesLaboral.maestros.trabajadores.model.Trabajador;
import com.gesLaboral.maestros.usuarios.model.Usuario;
import com.gesLaboral.navigation.constantes.UrlsNavigation;

public class TrabajadorControllerImpl extends GenericoControllerImpl<Trabajador> implements TrabajadorController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2210919759458414299L;
	private String filtroCodigo;
	private String filtroNombre;
	private String filtroApellido1;
	private String filtroApellido2;

	public TrabajadorControllerImpl() {
		super();
		setModel(new Trabajador());
	}

	@PostConstruct
	public void init() {
		LinkedList<Filtro> filtros = new LinkedList<Filtro>();

		Usuario usuario = getSessionController().getUsuarioSesion();

		if (usuario != null) {
			Filtro usuarioFiltro = new Filtro();
			usuarioFiltro.setTipoFiltro(EnumTipoFiltro.UNDEFINED);
			List<Filtro> filtrosUsuario = new ArrayList<>();
			Filtro filtroCrea = new Filtro();
			filtroCrea.setCampo("usuarioCreacion");
			filtroCrea.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroCrea.setValue(usuario);
			filtroCrea.setTienenSegundaCondicion(true);
			Filtro filtroMod = new Filtro();
			filtroMod.setCampo("usuarioModificacion");
			filtroMod.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroMod.setValue(usuario);
			filtroMod.setTipoFiltro2(EnumTipoFiltro.O);
			filtrosUsuario.add(filtroCrea);
			filtrosUsuario.add(filtroMod);
			usuarioFiltro.setFiltros(filtrosUsuario);
			filtros.add(usuarioFiltro);
			getService().setDefaultFiltros(filtros);
		}
		GenericoLazyTable<Trabajador> lazyTable = getLazyTable();
		lazyTable.setGenericoService(getService());

		String[] columns = { "codigo", "nombre", "apellido1", "apellido2", "fechaAlta", "fechaBaja" };
		String[] columnsSorters = { "codigo", "nombre", "apellido1", "apellido2", "fechaAlta", "fechaBaja" };
		String[] columnsText = { "trabajador.codigo", "trabajador.nombre", "trabajador.apellido1",
				"trabajador.apellido2", "trabajador.fechaAlta", "trabajador.fechaBaja" };
		lazyTable.setNumColums(columns.length);
		lazyTable.generaColumsn(columns, columnsSorters, columnsText);
		lazyTable.setHeader("trabajador.header");

	}

	@Override
	public String actionGoAdd() {
		super.actionGoAdd();
		getModel().setEmpresa(new Empresa());
		RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMTRABAJADOR + "').show()");
		RequestContext.getCurrentInstance().update("trabajadorAbm");
		return null;
	}

	@Override
	public String actionGoMod() {
		if (getLazyTable() != null && getLazyTable().getSelection() != null
				&& getLazyTable().getSelection().getCodigo() != null
				&& !getLazyTable().getSelection().getCodigo().trim().isEmpty()) {
			super.actionGoMod();
			if (getModel().getEmpresa() == null) {
				getModel().setEmpresa(new Empresa());
			}
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMTRABAJADOR + "').show()");
			RequestContext.getCurrentInstance().update("trabajadorAbm");
		} else {
			getIdiomaController().addMessageError("seleciona.registro");
		}
		return null;
	}

	@Override
	public String actionGoShow() {
		if (getLazyTable() != null && getLazyTable().getSelection() != null
				&& getLazyTable().getSelection().getCodigo() != null
				&& !getLazyTable().getSelection().getCodigo().trim().isEmpty()) {
			super.actionGoShow();
			if (getModel().getEmpresa() == null) {
				getModel().setEmpresa(new Empresa());
			}
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMTRABAJADOR + "').show()");
			RequestContext.getCurrentInstance().update("trabajadorAbm");
		} else {
			getIdiomaController().addMessageError("seleciona.registro");
		}
		return null;
	}

	@Override
	public String actionGoDel() {
		if (getLazyTable() != null && getLazyTable().getSelection() != null
				&& getLazyTable().getSelection().getCodigo() != null
				&& !getLazyTable().getSelection().getCodigo().trim().isEmpty()) {
			super.actionGoDel();
			RequestContext.getCurrentInstance().execute("PF('delTrabajador').show()");
		} else {
			getIdiomaController().addMessageError("seleciona.registro");
		}
		return null;
	}

	@Override
	public String addModel(String codigo) {
		if (codigo != null && !codigo.trim().isEmpty() && getModel() != null) {
			getModel().setUsuarioCreacion(getSessionController().getUsuarioSesion());
			getModel().setUsuarioModificacion(getSessionController().getUsuarioSesion());
		}
		if (getModel().getEmpresa() == null || getModel().getEmpresa().getCodigo() == null
				|| getModel().getEmpresa().getCodigo().trim().isEmpty()) {
			getModel().setEmpresa(null);
		}
		return super.addModel(codigo);
	}
	
	@Override
	public String delModel() {
		if (getModel() == null || getModel().getId() == null) {
			getIdiomaController().addMessageInfo("error.borrar");
			return null;
		} else {
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
			getIdiomaController().addMessageInfo("borrado.exito");
		}
		super.delModel();
		RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMTRABAJADOR + "').hide()");
		return null;
	}

	@Override
	public String cancelAbmAction() {
		if (isAdd()) {
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMTRABAJADOR + "').hide()");
		}
		return super.cancelAbmAction();
	}
	
	public String getFiltroCodigo() {
		return filtroCodigo;
	}
	
	public void setFiltroCodigo(String filtroCodigo) {
		this.filtroCodigo = filtroCodigo;
	}
	
	public String getFiltroApellido1() {
		return filtroApellido1;
	}
	
	public void setFiltroApellido1(String filtroApellido1) {
		this.filtroApellido1 = filtroApellido1;
	}
	
	public String getFiltroApellido2() {
		return filtroApellido2;
	}
	
	public void setFiltroApellido2(String filtroApellido2) {
		this.filtroApellido2 = filtroApellido2;
	}
	
	public String getFiltroNombre() {
		return filtroNombre;
	}
	
	public void setFiltroNombre(String filtroNombre) {
		this.filtroNombre = filtroNombre;
	}
	
	@Override
	public String aplicarFiltros() {
		LinkedList<Filtro> filtros = new LinkedList<>();
		if (filtroCodigo != null && !filtroCodigo.trim().isEmpty()) {
			Filtro filtro = new Filtro();
			filtro.setCampo("codigo");
			filtro.setTipoFiltro(EnumTipoFiltro.LIKE_TODO);
			filtro.setValue(this.filtroCodigo);
			filtros.add(filtro);
		}
		if (filtroNombre != null && !filtroNombre.trim().isEmpty()) {
			Filtro filtro = new Filtro();
			filtro.setCampo("nombre");
			filtro.setTipoFiltro(EnumTipoFiltro.LIKE_TODO);
			filtro.setValue(this.filtroNombre);
			filtros.add(filtro);
		}
		if (filtroApellido1 != null && !filtroApellido1.trim().isEmpty()) {
			Filtro filtro = new Filtro();
			filtro.setCampo("apellido1");
			filtro.setTipoFiltro(EnumTipoFiltro.LIKE_TODO);
			filtro.setValue(this.filtroApellido1);
			filtros.add(filtro);
		}
		if (filtroApellido2 != null && !filtroApellido2.trim().isEmpty()) {
			Filtro filtro = new Filtro();
			filtro.setCampo("apellido2");
			filtro.setTipoFiltro(EnumTipoFiltro.LIKE_TODO);
			filtro.setValue(this.filtroApellido2);
			filtros.add(filtro);
		}
		getLazyTable().setFiltros(filtros);
		return null;
	}
}

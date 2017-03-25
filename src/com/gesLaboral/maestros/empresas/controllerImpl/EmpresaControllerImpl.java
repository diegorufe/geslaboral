package com.gesLaboral.maestros.empresas.controllerImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.genericos.filtros.EnumTipoFiltro;
import com.gesLaboral.genericos.filtros.Filtro;
import com.gesLaboral.genericos.lazyTable.GenericoLazyTable;
import com.gesLaboral.maestros.empresas.controller.EmpresaController;
import com.gesLaboral.maestros.empresas.model.Empresa;
import com.gesLaboral.maestros.usuarios.model.Usuario;
import com.gesLaboral.navigation.constantes.UrlsNavigation;

public class EmpresaControllerImpl extends GenericoControllerImpl<Empresa> implements EmpresaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 477130532585025155L;
	private String filtroCodigo;
	private String filtroNombre;

	public EmpresaControllerImpl() {
		super();
		setModel(new Empresa());
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
		GenericoLazyTable<Empresa> lazyTable = getLazyTable();
		lazyTable.setGenericoService(getService());

		String[] columns = { "codigo", "nombre", "dir1", "poblacion", "provincia", "pais" };
		String[] columnsSorters = { "codigo", "nombre", "dir1", "poblacion", "provincia", "pais" };
		String[] columnsText = { "empresa.codigo", "empresa.nombre", "empresa.dir1", "empresa.poblacion",
				"empresa.provincia", "empresa.pais" };
		lazyTable.setNumColums(columns.length);
		lazyTable.generaColumsn(columns, columnsSorters, columnsText);
		lazyTable.setHeader("empresa.header");
	}

	@Override
	public String actionGoAdd() {
		super.actionGoAdd();
		RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMEMPRESA + "').show()");
		RequestContext.getCurrentInstance().update("empresaAbm");
		return null;
	}

	@Override
	public String actionGoMod() {
		if (getLazyTable() != null && getLazyTable().getSelection() != null
				&& getLazyTable().getSelection().getCodigo() != null
				&& !getLazyTable().getSelection().getCodigo().trim().isEmpty()) {
			super.actionGoMod();
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMEMPRESA + "').show()");
			RequestContext.getCurrentInstance().update("empresaAbm");
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
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMEMPRESA + "').show()");
			RequestContext.getCurrentInstance().update("empresaAbm");
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
			RequestContext.getCurrentInstance().execute("PF('delEmpresa').show()");
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
		RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMEMPRESA + "').hide()");
		return null;
	}

	@Override
	public String cancelAbmAction() {
		if (isAdd()) {
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMEMPRESA + "').hide()");
		}
		return super.cancelAbmAction();
	}

	public String getFiltroCodigo() {
		return filtroCodigo;
	}

	public void setFiltroCodigo(String filtroCodigo) {
		this.filtroCodigo = filtroCodigo;
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
		getLazyTable().setFiltros(filtros);
		return null;
	}

}

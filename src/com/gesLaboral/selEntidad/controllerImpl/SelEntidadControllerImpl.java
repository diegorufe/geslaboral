package com.gesLaboral.selEntidad.controllerImpl;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.genericos.lazyTable.GenericoLazyTable;
import com.gesLaboral.maestros.empresas.model.Empresa;
import com.gesLaboral.maestros.trabajadores.model.Trabajador;
import com.gesLaboral.navigation.constantes.UrlsNavigation;
import com.gesLaboral.selEntidad.controller.SelEntidadController;

@SuppressWarnings("rawtypes")
public class SelEntidadControllerImpl extends GenericoControllerImpl implements SelEntidadController {

	private String tipe;

	// public SelEntidadControllerImpl() {
	// super();
	// }

	@PostConstruct
	public void postConstructor() {
		if (tipe != null) {
			if (tipe.equalsIgnoreCase("empresa")) {
				initSelEmpresas();
			} else if (tipe.equalsIgnoreCase("trabajador")) {
				initSelTrabajador();
			} else if (tipe.equalsIgnoreCase("estadisticaTrabajador")) {
				initSelTrabajador();
			} else if (tipe.equalsIgnoreCase("estadisticaEmpresa")) {
				initSelEmpresas();
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3418331586435261239L;

	@SuppressWarnings("unchecked")
	@Override
	public void initSelEmpresas() {
		GenericoLazyTable<Empresa> selLazyTableEmpre = null;
		selLazyTableEmpre = new GenericoLazyTable<Empresa>();
		selLazyTableEmpre.setGenericoService(getEmpresasController().getService());
		String[] columns = { "codigo", "nombre" };
		String[] columnsSorters = { "codigo", "nombre" };
		String[] columnsText = { "empresa.codigo", "empresa.nombre" };
		selLazyTableEmpre.setNumColums(columns.length);
		selLazyTableEmpre.generaColumsn(columns, columnsSorters, columnsText);
		selLazyTableEmpre.setHeader("empresa.header");
		setLazyTable(selLazyTableEmpre);
	}

	@Override
	public void onSelect(final SelectEvent event) {
		if (event.getObject() != null) {
			if (tipe != null && tipe.equalsIgnoreCase("empresa")) {

				Empresa empresa = (Empresa) event.getObject();
				if (getTrabajadorController().getModel() != null) {
					getEmpresasController().getService().update(empresa);
					getTrabajadorController().getModel().setEmpresa(empresa);
					RequestContext.getCurrentInstance().update("trabajadorAbm");
					RequestContext.getCurrentInstance()
							.execute("PF('" + UrlsNavigation.DIALOG_SEL_EMPRESA + "').hide()");
					RequestContext.getCurrentInstance().update(UrlsNavigation.DIALOG_SEL_EMPRESA);
					RequestContext.getCurrentInstance().update("trabajadorAbm");
					getTrabajadorController().getModel().setCodigoEmpresa(empresa.getCodigo());

				}
			} else if (tipe != null && tipe.equalsIgnoreCase("trabajador")) {
				Trabajador trabajador = (Trabajador) event.getObject();
				if (trabajador != null && trabajador.getCodigo() != null && !trabajador.getCodigo().trim().isEmpty()) {
					getTrabajadorController().getService().update(trabajador);
					getJornadaController().setTrabajadorFiltro(trabajador);
					RequestContext.getCurrentInstance()
							.update("formJornadas:trabajadorDiv:divTextSel:selTrabajadorText");
					RequestContext.getCurrentInstance().update("selTrabajadorText");
					RequestContext.getCurrentInstance().update("panelFiltrosJornada");
					RequestContext.getCurrentInstance().update(":formJornadas:panelFiltrosJornada");
					RequestContext.getCurrentInstance()
							.execute("PF('" + UrlsNavigation.DIALOG_SEL_TRABAJADOR + "').hide()");
				}
			} else if (tipe != null && tipe.equalsIgnoreCase("estadisticaTrabajador")) {
				Trabajador trabajador = (Trabajador) event.getObject();
				if (trabajador != null && trabajador.getCodigo() != null && !trabajador.getCodigo().trim().isEmpty()) {
					getTrabajadorController().getService().update(trabajador);
					getEstadisiticaTrabajadorController().setTrabajadorFiltro(trabajador);
					RequestContext.getCurrentInstance()
							.update("formEstadisticas:frm:trabajadorDiv:divTextSel:selTrabajadorText");
					RequestContext.getCurrentInstance().update("selTrabajadorText");
					RequestContext.getCurrentInstance().update("panelFiltrosJornada");
					RequestContext.getCurrentInstance().update(":formEstadisticas:frm:panelFiltrosJornada");
					RequestContext.getCurrentInstance()
							.execute("PF('" + UrlsNavigation.DIALOG_SEL_TRABAJADOR + "').hide()");
				}
			} else if (tipe != null && tipe.equalsIgnoreCase("estadisticaEmpresa")) {
				Empresa empresa = (Empresa) event.getObject();
				if (empresa != null && empresa.getCodigo() != null && !empresa.getCodigo().trim().isEmpty()) {
					getEmpresasController().getService().update(empresa);
					getEstadisiticaEmpresaController().setEmpresaFiltro(empresa);
					RequestContext.getCurrentInstance()
							.update("formEstadisticas:frm:empresaDiv:divTextSel:selEmpresaText");
					RequestContext.getCurrentInstance().update("selEmpresaText");
					RequestContext.getCurrentInstance().update("panelFiltrosJornada");
					RequestContext.getCurrentInstance().update(":formEstadisticas:frm:panelFiltrosJornada");
					RequestContext.getCurrentInstance()
							.execute("PF('" + UrlsNavigation.DIALOG_SEL_EMPRESA + "').hide()");
				}
			}
		}
	}

	@Override
	public String addEntidad() {
		if (tipe != null && tipe.equalsIgnoreCase("empresa")) {
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_SEL_EMPRESA + "').show()");
			RequestContext.getCurrentInstance().update("selEmpresaFrm");
		} else if (tipe != null && tipe.equalsIgnoreCase("trabajador")) {
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_SEL_TRABAJADOR + "').show()");
			RequestContext.getCurrentInstance().update("selTrabajadorFrm");
		} else if (tipe != null && tipe.equalsIgnoreCase("estadisticaTrabajador")) {
			RequestContext.getCurrentInstance().update("selTrabajadorFrm");
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_SEL_TRABAJADOR + "').show()");
		} else if (tipe != null && tipe.equalsIgnoreCase("estadisticaEmpresa")) {
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_SEL_EMPRESA + "').show()");
			RequestContext.getCurrentInstance().update("selEmpresaFrm");
		}
		return null;
	}

	@Override
	public String delEntidad() {
		if (tipe != null && tipe.equalsIgnoreCase("empresa")) {
			getTrabajadorController().getModel().setEmpresa(new Empresa());
			RequestContext.getCurrentInstance().update("trabajadorAbm");
			RequestContext.getCurrentInstance().update("trabajadorAbm");
			getTrabajadorController().getModel().setCodigoEmpresa("");
		} else if (tipe != null && tipe.equalsIgnoreCase("trabajador")) {
			getJornadaController().setTrabajadorFiltro(new Trabajador());
			RequestContext.getCurrentInstance().update("formJornadas:trabajadorDiv:divTextSel:selTrabajadorText");
			RequestContext.getCurrentInstance().update("selTrabajadorText");
			RequestContext.getCurrentInstance().update("panelFiltrosJornada");
			RequestContext.getCurrentInstance().update(":formJornadas:panelFiltrosJornada");
		} else if (tipe != null && tipe.equalsIgnoreCase("estadisticaTrabajador")) {
			getEstadisiticaTrabajadorController().setTrabajadorFiltro(new Trabajador());
			RequestContext.getCurrentInstance()
					.update("formEstadisticas:frm:trabajadorDiv:divTextSel:selTrabajadorText");
			RequestContext.getCurrentInstance().update("selTrabajadorText");
			RequestContext.getCurrentInstance().update("panelFiltrosJornada");
			RequestContext.getCurrentInstance().update(":formEstadisticas:frm:panelFiltrosJornada");
		} else if (tipe != null && tipe.equalsIgnoreCase("estadisticaEmpresa")) {
			getEstadisiticaEmpresaController().setEmpresaFiltro(new Empresa());
			RequestContext.getCurrentInstance()
					.update("formEstadisticas:frm:empresaDiv:divTextSel:selEmpresaText");
			RequestContext.getCurrentInstance().update("selEmpresaText");
			RequestContext.getCurrentInstance().update("panelFiltrosJornada");
			RequestContext.getCurrentInstance().update(":formEstadisticas:frm:panelFiltrosJornada");
		}
		return null;
	}

	public String getTipe() {
		return tipe;
	}

	public void setTipe(String tipe) {
		this.tipe = tipe;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initSelTrabajador() {
		GenericoLazyTable<Trabajador> selLazyTable = null;
		selLazyTable = new GenericoLazyTable<Trabajador>();
		selLazyTable.setGenericoService(getTrabajadorController().getService());
		String[] columns = { "codigo", "nombre", "apellido1", "apellido2", "fechaAlta", "fechaBaja" };
		String[] columnsSorters = { "codigo", "nombre", "apellido1", "apellido2", "fechaAlta", "fechaBaja" };
		String[] columnsText = { "trabajador.codigo", "trabajador.nombre", "trabajador.apellido1",
				"trabajador.apellido2", "trabajador.fechaAlta", "trabajador.fechaBaja" };
		selLazyTable.setNumColums(columns.length);
		selLazyTable.generaColumsn(columns, columnsSorters, columnsText);
		selLazyTable.setHeader("trabajador.header");
		setLazyTable(selLazyTable);
	}

}

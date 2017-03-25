package com.gesLaboral.maestros.estadisticas.controllerImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.JoinType;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.genericos.filtros.EnumTipoFiltro;
import com.gesLaboral.genericos.filtros.Filtro;
import com.gesLaboral.idioma.controller.IdiomaController;
import com.gesLaboral.maestros.empresas.model.Empresa;
import com.gesLaboral.maestros.estadisticas.controller.EstadisticaController;
import com.gesLaboral.maestros.jornadas.constantes.EnumTipos;
import com.gesLaboral.maestros.jornadas.service.JornadaService;
import com.gesLaboral.maestros.trabajadores.model.Trabajador;
import com.gesLaboral.maestros.usuarios.model.Usuario;

@SuppressWarnings("rawtypes")
public class EstadisiticaControllerImpl extends GenericoControllerImpl implements EstadisticaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8862912239976119830L;
	private Trabajador trabajadorFiltro;
	private Empresa empresaFiltro;
	private Usuario usuarioFiltro;
	private BarChartModel barCharModel;
	private int anio;

	public EstadisiticaControllerImpl() {
		super();
		trabajadorFiltro = new Trabajador();
		empresaFiltro = new Empresa();
		barCharModel = new BarChartModel();
		anio = Calendar.getInstance().get(Calendar.YEAR);
		usuarioFiltro = getSessionController().getUsuarioSesion();
	}

	public Trabajador getTrabajadorFiltro() {
		return trabajadorFiltro;
	}

	public void setTrabajadorFiltro(Trabajador trabajadorFiltro) {
		this.trabajadorFiltro = trabajadorFiltro;
	}

	@Override
	public String aplicarFiltrosTrabajador() {
		if (anio <= 0) {
			anio = Calendar.getInstance().get(Calendar.YEAR);
		}
		barCharModel.clear();
		LinkedHashMap<Integer, HashMap<String, Integer>> mesesDatos = new LinkedHashMap<>();
		if (trabajadorFiltro != null && trabajadorFiltro.getCodigo() != null
				&& !trabajadorFiltro.getCodigo().trim().isEmpty()) {
			IdiomaController idioma = getIdiomaController();
			LinkedList<Filtro> filtros = null;
			JornadaService service = (JornadaService) getJornadaController().getService();
			Filtro filtroTraba = new Filtro();
			filtroTraba.setCampo("trabajador");
			filtroTraba.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroTraba.setValue(trabajadorFiltro);
			Filtro filtroFecha = null;
			Filtro filtroFechaIni = null;
			Filtro filtroFechaFin = null;
			Filtro filtroTIpo = null;
			Calendar fechaIni = null;
			Calendar fechaFin = null;
			List<Filtro> filtrosFechas = null;
			int count = 0;
			HashMap<String, Integer> mesDato = null;
			for (int i = 1; i <= 12; i++) {

				mesDato = new HashMap<>();
				fechaIni = Calendar.getInstance();
				fechaFin = Calendar.getInstance();
				fechaIni.set(anio, i, 1, 0, 0, 0);
				fechaFin.set(anio, i, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
				// Filtros
				filtrosFechas = new LinkedList<>();
				filtroFecha = new Filtro();
				filtroFecha.setTipoFiltro(EnumTipoFiltro.UNDEFINED);
				filtroFechaIni = new Filtro();
				filtroFechaIni.setCampo("fechaInicio");
				filtroFechaIni.setTienenSegundaCondicion(true);
				filtroFechaIni.setTipoFiltro(EnumTipoFiltro.ENTRE);
				filtroFechaIni.setValue(fechaIni.getTime());
				filtroFechaIni.setValue2(fechaFin.getTime());
				filtroFechaFin = new Filtro();
				filtroFechaFin.setCampo("fechaFin");
				filtroFechaFin.setTipoFiltro(EnumTipoFiltro.ENTRE);
				filtroFechaFin.setTipoFiltro2(EnumTipoFiltro.O);
				filtroFechaFin.setValue(fechaIni);
				filtroFechaFin.setValue2(fechaFin);
				filtrosFechas.add(filtroFechaIni);
				filtrosFechas.add(filtroFechaFin);
				filtroFecha.setFiltros(filtrosFechas);
				EnumTipos[] tipos = EnumTipos.values();
				for (EnumTipos enumTipos : tipos) {
					if (enumTipos.getValue().trim().isEmpty()) {
						continue;
					}
					filtros = new LinkedList<>();
					filtroTIpo = new Filtro();
					filtroTIpo.setCampo("tipo");
					filtroTIpo.setTipoFiltro(EnumTipoFiltro.IGUAL);
					filtroTIpo.setValue(enumTipos.getValue2());
					filtros.add(filtroTraba);
					filtros.add(filtroFecha);
					filtros.add(filtroTIpo);
					count = service.countByQuery(null, filtros);
					mesDato.put(enumTipos.getValue2(), count);
				}
				mesesDatos.put(i, mesDato);
			}
			ChartSeries chartSeries = null;
			EnumTipos[] tipos = EnumTipos.values();

			for (EnumTipos enumTipos : tipos) {
				if (enumTipos.getValue().trim().isEmpty()) {
					continue;
				}
				chartSeries = new ChartSeries();
				for (int i = 1; i <= 12; i++) {
					chartSeries.setLabel(idioma.getTraducion("jornada." + enumTipos.getValue2()));
					chartSeries.set(String.valueOf(i), mesesDatos.get(i).get(enumTipos.getValue2()));
				}
				barCharModel.addSeries(chartSeries);
			}
			barCharModel.setTitle(idioma.getTraducion("estadisticasTrabajador") + " " + anio);
			barCharModel.setSeriesColors("8B0000,F0E68C,9932CC,FF0000,FF8C00,DB7093,87CEEB,CD853F,7FFFD4,32CD32");
			// barCharModel.setAnimate(true);
			barCharModel.setLegendPosition("ne");

		}

		return null;
	}

	public BarChartModel getBarCharModel() {
		return barCharModel;
	}

	public void setBarCharModel(BarChartModel barCharModel) {
		this.barCharModel = barCharModel;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public Empresa getEmpresaFiltro() {
		return empresaFiltro;
	}

	public void setEmpresaFiltro(Empresa empresaFiltro) {
		this.empresaFiltro = empresaFiltro;
	}

	public Usuario getUsuarioFiltro() {
		return usuarioFiltro;
	}

	public void setUsuarioFiltro(Usuario usuarioFiltro) {
		this.usuarioFiltro = usuarioFiltro;
	}

	@Override
	public String aplicarFiltrosUsuario() {
		if (anio <= 0) {
			anio = Calendar.getInstance().get(Calendar.YEAR);
		}
		barCharModel.clear();
		LinkedHashMap<Integer, HashMap<String, Integer>> mesesDatos = new LinkedHashMap<>();
		if (usuarioFiltro != null && usuarioFiltro.getNick() != null
				&& !usuarioFiltro.getNick().trim().isEmpty()) {
			IdiomaController idioma = getIdiomaController();
			LinkedList<Filtro> filtros = null;
			JornadaService service = (JornadaService) getJornadaController().getService();
			List<Filtro> filtrosUsuario = new ArrayList<>();
			Filtro filtroUsuario = new Filtro();
			filtroUsuario.setTipoFiltro(EnumTipoFiltro.UNDEFINED);
			Filtro filtroUsuarioCreate = new Filtro();
			filtroUsuarioCreate.setCampo("trabajador");
			filtroUsuarioCreate.setCampoJoin("usuarioCreacion");
			filtroUsuarioCreate.setJoin(JoinType.LEFT);
			filtroUsuarioCreate.setTienenSegundaCondicion(true);
			filtroUsuarioCreate.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroUsuarioCreate.setValue(usuarioFiltro);
			Filtro filtroUsuarioMod = new Filtro();
			filtroUsuarioMod.setCampo("trabajador");
			filtroUsuarioMod.setCampoJoin("usuarioModificacion");
			filtroUsuarioMod.setJoin(JoinType.LEFT);
			filtroUsuarioMod.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroUsuarioMod.setTipoFiltro2(EnumTipoFiltro.O);
			filtroUsuarioMod.setValue(usuarioFiltro);
			filtrosUsuario.add(filtroUsuarioCreate);
			filtrosUsuario.add(filtroUsuarioMod);
			filtroUsuario.setFiltros(filtrosUsuario);
			Filtro filtroFecha = null;
			Filtro filtroFechaIni = null;
			Filtro filtroFechaFin = null;
			Filtro filtroTIpo = null;
			Calendar fechaIni = null;
			Calendar fechaFin = null;
			List<Filtro> filtrosFechas = null;
			int count = 0;
			HashMap<String, Integer> mesDato = null;
			for (int i = 1; i <= 12; i++) {

				mesDato = new HashMap<>();
				fechaIni = Calendar.getInstance();
				fechaFin = Calendar.getInstance();
				fechaIni.set(anio, i, 1, 0, 0, 0);
				fechaFin.set(anio, i, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
				// Filtros
				filtrosFechas = new LinkedList<>();
				filtroFecha = new Filtro();
				filtroFecha.setTipoFiltro(EnumTipoFiltro.UNDEFINED);
				filtroFechaIni = new Filtro();
				filtroFechaIni.setCampo("fechaInicio");
				filtroFechaIni.setTienenSegundaCondicion(true);
				filtroFechaIni.setTipoFiltro(EnumTipoFiltro.ENTRE);
				filtroFechaIni.setValue(fechaIni.getTime());
				filtroFechaIni.setValue2(fechaFin.getTime());
				filtroFechaFin = new Filtro();
				filtroFechaFin.setCampo("fechaFin");
				filtroFechaFin.setTipoFiltro(EnumTipoFiltro.ENTRE);
				filtroFechaFin.setTipoFiltro2(EnumTipoFiltro.O);
				filtroFechaFin.setValue(fechaIni);
				filtroFechaFin.setValue2(fechaFin);
				filtrosFechas.add(filtroFechaIni);
				filtrosFechas.add(filtroFechaFin);
				filtroFecha.setFiltros(filtrosFechas);
				EnumTipos[] tipos = EnumTipos.values();
				for (EnumTipos enumTipos : tipos) {
					if (enumTipos.getValue().trim().isEmpty()) {
						continue;
					}
					filtros = new LinkedList<>();
					filtroTIpo = new Filtro();
					filtroTIpo.setCampo("tipo");
					filtroTIpo.setTipoFiltro(EnumTipoFiltro.IGUAL);
					filtroTIpo.setValue(enumTipos.getValue2());
					filtros.add(filtroUsuario);
					filtros.add(filtroFecha);
					filtros.add(filtroTIpo);
					count = service.countByQuery(null, filtros);
					mesDato.put(enumTipos.getValue2(), count);
				}
				mesesDatos.put(i, mesDato);
			}
			ChartSeries chartSeries = null;
			EnumTipos[] tipos = EnumTipos.values();

			for (EnumTipos enumTipos : tipos) {
				if (enumTipos.getValue().trim().isEmpty()) {
					continue;
				}
				chartSeries = new ChartSeries();
				for (int i = 1; i <= 12; i++) {
					chartSeries.setLabel(idioma.getTraducion("jornada." + enumTipos.getValue2()));
					chartSeries.set(String.valueOf(i), mesesDatos.get(i).get(enumTipos.getValue2()));
				}
				barCharModel.addSeries(chartSeries);
			}
			barCharModel.setTitle(idioma.getTraducion("estadisticasUsuario") + " " + anio);
			barCharModel.setSeriesColors("8B0000,F0E68C,9932CC,FF0000,FF8C00,DB7093,87CEEB,CD853F,7FFFD4,32CD32");
			// barCharModel.setAnimate(true);
			barCharModel.setLegendPosition("ne");

		}

		return null;
	}

	@Override
	public String aplicarFiltrosEmpresa() {
		if (anio <= 0) {
			anio = Calendar.getInstance().get(Calendar.YEAR);
		}
		barCharModel.clear();
		LinkedHashMap<Integer, HashMap<String, Integer>> mesesDatos = new LinkedHashMap<>();
		if (empresaFiltro != null && empresaFiltro.getCodigo() != null
				&& !empresaFiltro.getCodigo().trim().isEmpty()) {
			IdiomaController idioma = getIdiomaController();
			LinkedList<Filtro> filtros = null;
			JornadaService service = (JornadaService) getJornadaController().getService();
			Filtro filtroEmpresa = new Filtro();
			filtroEmpresa.setCampo("trabajador");
			filtroEmpresa.setCampoJoin("empresa");
			filtroEmpresa.setJoin(JoinType.LEFT);
			filtroEmpresa.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroEmpresa.setValue(empresaFiltro);
			Filtro filtroFecha = null;
			Filtro filtroFechaIni = null;
			Filtro filtroFechaFin = null;
			Filtro filtroTIpo = null;
			Calendar fechaIni = null;
			Calendar fechaFin = null;
			List<Filtro> filtrosFechas = null;
			int count = 0;
			HashMap<String, Integer> mesDato = null;
			for (int i = 1; i <= 12; i++) {

				mesDato = new HashMap<>();
				fechaIni = Calendar.getInstance();
				fechaFin = Calendar.getInstance();
				fechaIni.set(anio, i, 1, 0, 0, 0);
				fechaFin.set(anio, i, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
				// Filtros
				filtrosFechas = new LinkedList<>();
				filtroFecha = new Filtro();
				filtroFecha.setTipoFiltro(EnumTipoFiltro.UNDEFINED);
				filtroFechaIni = new Filtro();
				filtroFechaIni.setCampo("fechaInicio");
				filtroFechaIni.setTienenSegundaCondicion(true);
				filtroFechaIni.setTipoFiltro(EnumTipoFiltro.ENTRE);
				filtroFechaIni.setValue(fechaIni.getTime());
				filtroFechaIni.setValue2(fechaFin.getTime());
				filtroFechaFin = new Filtro();
				filtroFechaFin.setCampo("fechaFin");
				filtroFechaFin.setTipoFiltro(EnumTipoFiltro.ENTRE);
				filtroFechaFin.setTipoFiltro2(EnumTipoFiltro.O);
				filtroFechaFin.setValue(fechaIni);
				filtroFechaFin.setValue2(fechaFin);
				filtrosFechas.add(filtroFechaIni);
				filtrosFechas.add(filtroFechaFin);
				filtroFecha.setFiltros(filtrosFechas);
				EnumTipos[] tipos = EnumTipos.values();
				
				for (EnumTipos enumTipos : tipos) {
					if (enumTipos.getValue().trim().isEmpty()) {
						continue;
					}
					filtros = new LinkedList<>();
					filtroTIpo = new Filtro();
					filtroTIpo.setCampo("tipo");
					filtroTIpo.setTipoFiltro(EnumTipoFiltro.IGUAL);
					filtroTIpo.setValue(enumTipos.getValue2());
					filtros.add(filtroEmpresa);
					filtros.add(filtroFecha);
					filtros.add(filtroTIpo);
					count = service.countByQuery(null, filtros);
					mesDato.put(enumTipos.getValue2(), count);
				}
				mesesDatos.put(i, mesDato);
			}
			ChartSeries chartSeries = null;
			EnumTipos[] tipos = EnumTipos.values();

			for (EnumTipos enumTipos : tipos) {
				if (enumTipos.getValue().trim().isEmpty()) {
					continue;
				}
				chartSeries = new ChartSeries();
				for (int i = 1; i <= 12; i++) {
					chartSeries.setLabel(idioma.getTraducion("jornada." + enumTipos.getValue2()));
					chartSeries.set(String.valueOf(i), mesesDatos.get(i).get(enumTipos.getValue2()));
				}
				barCharModel.addSeries(chartSeries);
				barCharModel.setSeriesColors("8B0000,F0E68C,9932CC,FF0000,FF8C00,DB7093,87CEEB,CD853F,7FFFD4,32CD32");
			}
			barCharModel.setTitle(idioma.getTraducion("estadisticasEmpresa") + " " + anio);
			// barCharModel.setAnimate(true);
			barCharModel.setLegendPosition("ne");

		}

		return null;
	}

}

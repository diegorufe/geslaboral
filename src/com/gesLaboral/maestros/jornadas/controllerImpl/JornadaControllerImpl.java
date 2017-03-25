package com.gesLaboral.maestros.jornadas.controllerImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import com.gesLaboral.genericos.controllerImpl.GenericoControllerImpl;
import com.gesLaboral.genericos.filtros.EnumTipoFiltro;
import com.gesLaboral.genericos.filtros.Filtro;
import com.gesLaboral.maestros.jornadas.constantes.EnumTipos;
import com.gesLaboral.maestros.jornadas.controller.JornadaController;
import com.gesLaboral.maestros.jornadas.model.Jornada;
import com.gesLaboral.maestros.jornadas.service.JornadaService;
import com.gesLaboral.maestros.jornadas.serviceImpl.JornadaServiceImpl;
import com.gesLaboral.maestros.trabajadores.model.Trabajador;
import com.gesLaboral.navigation.constantes.UrlsNavigation;

public class JornadaControllerImpl extends GenericoControllerImpl<Jornada> implements JornadaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3735433693030122127L;
	private ScheduleModel eventModel;
	private ScheduleEvent event;
	private LinkedList<Filtro> filtros;
	private List<EnumTipos> tipos;
	private Trabajador trabajadorFiltro;

	public JornadaControllerImpl() {
		super();
		event = new DefaultScheduleEvent();
		trabajadorFiltro = new Trabajador();
		tipos = new ArrayList<>();
		filtros = new LinkedList<>();
		tipos.add(EnumTipos.ACCIDENTE);
		tipos.add(EnumTipos.AUSENCIA);
		tipos.add(EnumTipos.ENFERMEDAD);
		tipos.add(EnumTipos.FESTIVO);
		tipos.add(EnumTipos.HUELGA);
		tipos.add(EnumTipos.MATERNIDAD);
		tipos.add(EnumTipos.PATERNIDAD);
		tipos.add(EnumTipos.PERMISO);
		tipos.add(EnumTipos.TRABAJO);
		tipos.add(EnumTipos.VACACIONES);
		setModel(new Jornada());
	}

	@PostConstruct
	public void postConstructor() {

		eventModel = new LazyScheduleModel() {

			private static final long serialVersionUID = -5230310001379616638L;

			@Override
			public void loadEvents(Date start, Date end) {
//				Calendar starCal = Calendar.getInstance();
//				Calendar endCal = Calendar.getInstance();
				
				if (filtros != null && filtros.size() > 0) {
					LinkedList<Filtro> filtrosFec =new LinkedList<>();
					Filtro filtroFechas = new Filtro();
					filtroFechas.setTipoFiltro(EnumTipoFiltro.UNDEFINED);
					List<Filtro> filtrosFechas = new ArrayList<>();
					Filtro filtroFechaIni = new Filtro();
					filtroFechaIni.setTipoFiltro(EnumTipoFiltro.ENTRE);
					filtroFechaIni.setCampo("fechaInicio");
					filtroFechaIni.setTienenSegundaCondicion(true);
					filtroFechaIni.setValue(start);
					filtroFechaIni.setValue2(end);
					Filtro filtroFechaFin = new Filtro();
					filtroFechaFin.setTipoFiltro(EnumTipoFiltro.ENTRE);
					filtroFechaFin.setTipoFiltro2(EnumTipoFiltro.O);
					filtroFechaFin.setCampo("fechaFin");
					filtroFechaFin.setValue(start);
					filtroFechaFin.setValue2(end);
					filtrosFechas.add(filtroFechaIni);
					filtrosFechas.add(filtroFechaFin);
					filtroFechas.setFiltros(filtrosFechas);
					filtrosFec.add(filtroFechas);
					filtrosFec.addAll(filtros);
					List<Jornada> jornadas = getService().findByQuery(null, filtrosFec, null);
					DefaultScheduleEvent event = null;
					if(jornadas != null && jornadas.size() > 0){
						for (Jornada jornada : jornadas) {
							if(jornada != null){
								event = new DefaultScheduleEvent();
								event.setEditable(false);
								event.setTitle(jornada.getDescri() != null ? jornada.getDescri() : "");
								event.setData(jornada);
								event.setStartDate(jornada.getFechaInicio());
								event.setEndDate(jornada.getFechaFin());
								event.setStyleClass("jornadas-"+jornada.getTipo());
								eventModel.addEvent(event);
							}
						}
					}
				}
			}
		};

	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public LinkedList<Filtro> getFiltros() {
		return filtros;
	}

	public void setFiltros(LinkedList<Filtro> filtros) {
		this.filtros = filtros;
	}

	@Override
	public void onEventSelect(SelectEvent selectEvent) {
		actionGoMod();
		event = (ScheduleEvent) selectEvent.getObject();
		if (event.getData() != null) {
			setModel((Jornada) event.getData());
		}
	}

	@Override
	public void onDateSelect(SelectEvent selectEvent) {
		actionGoAdd();
		Date fechaIni = (Date) selectEvent.getObject();
		Calendar fechaIniCal = Calendar.getInstance();
		fechaIniCal.setTime(fechaIni);
		fechaIniCal.set(fechaIniCal.get(Calendar.YEAR), fechaIniCal.get(Calendar.MONTH), fechaIniCal.get(Calendar.DATE), 0, 0,0);
		Calendar fechaFinCal = Calendar.getInstance();
		fechaFinCal.setTime(fechaIni);
		fechaFinCal.set(fechaFinCal.get(Calendar.YEAR), fechaFinCal.get(Calendar.MONTH), fechaFinCal.get(Calendar.DATE), 23,59,59);
		getModel().setFechaInicio(fechaIniCal.getTime());
		getModel().setFechaFin(fechaFinCal.getTime());
	}



	public List<EnumTipos> getTipos() {
		return tipos;
	}

	public void setTipos(List<EnumTipos> tipos) {
		this.tipos = tipos;
	}

	public Trabajador getTrabajadorFiltro() {
		return trabajadorFiltro;
	}
	
	public void setTrabajadorFiltro(Trabajador trabajadorFiltro) {
		this.trabajadorFiltro = trabajadorFiltro;
	}

	@Override
	public String actionGoAdd() {
		if(trabajadorFiltro == null || trabajadorFiltro.getCodigo() == null || trabajadorFiltro.getCodigo().trim().isEmpty()){
			getIdiomaController().addMessageError("seleciona.trabajador");
			return null;
		}
		super.actionGoAdd();
		RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMJORNADA + "').show()");
		RequestContext.getCurrentInstance().update("jornadaAbm");
		return null;
	}

	@Override
	public String actionGoMod() {
		super.actionGoMod();
		RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMJORNADA + "').show()");
		RequestContext.getCurrentInstance().update("jornadaAbm");
		return null;
	}

	@Override
	public String addModel() {
		boolean valido = true;
		if (getModel() != null) {
			if (getModel().getFechaInicio() == null) {
				getIdiomaController().addMessageError("jornada.fechaInicio.necesaria");
				valido = false;
			}
			if (getModel().getFechaFin() == null) {
				getIdiomaController().addMessageError("jornada.fechaFin.necesaria");
				valido = false;
			}
			if (trabajadorFiltro != null && getModel().getFechaInicio() != null && getModel().getFechaFin() != null) {
				Jornada jornadaFechas = ((JornadaService) getService()).findJornadaFechas(trabajadorFiltro,
						getModel().getFechaInicio(), getModel().getFechaFin());
				if (jornadaFechas != null) {
					getIdiomaController().addMessageError("jornada.existente");
					valido = false;
				}
			}
		}
		if (valido) {
			getModel().setTrabajador(trabajadorFiltro);
			getService().save(getModel());
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMJORNADA + "').hide()");
			RequestContext.getCurrentInstance().update("formJornadas:jornadasSchedule");
			RequestContext.getCurrentInstance().update("jornadasSchedule");
		}
		return null;
	}

	@Override
	public String modModel() {
		boolean valido = true;
		if (getModel() != null) {
			if (getModel().getFechaInicio() == null) {
				getIdiomaController().addMessageError("jornada.fechaInicio.necesaria");
				valido = false;
			}
			if (getModel().getFechaFin() == null) {
				getIdiomaController().addMessageError("jornada.fechaFin.necesaria");
				valido = false;
			}
			if (trabajadorFiltro != null && getModel().getFechaInicio() != null && getModel().getFechaFin() != null) {
				Jornada jornadaFechas = ((JornadaServiceImpl) getService()).findJornadaFechas(trabajadorFiltro,
						getModel().getFechaInicio(), getModel().getFechaFin());
				if (jornadaFechas != null && !jornadaFechas.getId().equals(getModel().getId())) {
					getIdiomaController().addMessageError("jornada.existente");
					valido = false;
				}
			}
		}
		if (valido) {
			getModel().setTrabajador(trabajadorFiltro);
			getService().update(getModel());
			RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMJORNADA + "').hide()");
			RequestContext.getCurrentInstance().update("formJornadas:jornadasSchedule");
			RequestContext.getCurrentInstance().update("jornadasSchedule");
		}
		return null;
	}

	@Override
	public String delModel() {
		super.delModel();
		RequestContext.getCurrentInstance().update("formJornadas:jornadasSchedule");
		RequestContext.getCurrentInstance().update("jornadasSchedule");
		RequestContext.getCurrentInstance().execute("PF('delJornadaDialog').hide()");
		RequestContext.getCurrentInstance().execute("PF('" + UrlsNavigation.DIALOG_ABMJORNADA + "').hide()");
		return null;
	}
	
	@Override
	public String aplicarFiltros() {
		filtros.clear();
		if(trabajadorFiltro != null && trabajadorFiltro.getCodigo() != null && !trabajadorFiltro.getCodigo().trim().isEmpty()){

			Filtro filtroTraba = new Filtro();
			filtroTraba.setCampo("trabajador");
			filtroTraba.setTipoFiltro(EnumTipoFiltro.IGUAL);
			filtroTraba.setValue(trabajadorFiltro);
			
			filtros.add(filtroTraba);
			RequestContext.getCurrentInstance().update("formJornadas:jornadasSchedule");
			RequestContext.getCurrentInstance().update("jornadasSchedule");
		}
		return super.aplicarFiltros();
	}

}

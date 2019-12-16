/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import Entidades.capacitacion.Area;
import Entidades.capacitacion.Destinatario;
import Entidades.capacitacion.Disertante;
import Facades.AreaFacade;
import Facades.DestinatarioFacade;
import Facades.DisertanteFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author hugo
 */
@Named(value = "pickListView")
@SessionScoped
public class PickListView implements Serializable {

    /**
     * Creates a new instance of PickListView
     */
    @EJB
    private AreaFacade areaFacade;
    @EJB
    private DestinatarioFacade destinatarioFacade;
    @EJB
    private DisertanteFacade disertanteFacade;

    private List<Area> source;
    private List<Area> target;
    private List<Destinatario> sourceDest;
    private List<Destinatario> targetDest;
    private List<Disertante> sourceDisert;
    private List<Disertante> targetDisert;
    private DualListModel<Area> areaDualListModel;
    private DualListModel<Destinatario> destDualListModel;
    private DualListModel<Disertante> disertDualListModel;

    public DisertanteFacade getDisertanteFacade() {
        return disertanteFacade;
    }

    public void setDisertanteFacade(DisertanteFacade disertanteFacade) {
        this.disertanteFacade = disertanteFacade;
    }

    public List<Disertante> getSourceDisert() {
        return sourceDisert;
    }

    public void setSourceDisert(List<Disertante> sourceDisert) {
        this.sourceDisert = sourceDisert;
    }

    public List<Disertante> getTargetDisert() {
        return targetDisert;
    }

    public void setTargetDisert(List<Disertante> targetDisert) {
        this.targetDisert = targetDisert;
    }

    public DualListModel<Disertante> getDisertDualListModel() {
        return disertDualListModel;
    }

    ///GETTERS AND SETTERS
    public void setDisertDualListModel(DualListModel<Disertante> disertDualListModel) {
        this.disertDualListModel = disertDualListModel;
    }

    public DestinatarioFacade getDestinatarioFacade() {
        return destinatarioFacade;
    }

    public void setDestinatarioFacade(DestinatarioFacade destinatarioFacade) {
        this.destinatarioFacade = destinatarioFacade;
    }

    public List<Destinatario> getSourceDest() {
        return sourceDest;
    }

    public void setSourceDest(List<Destinatario> sourceDest) {
        this.sourceDest = sourceDest;
    }

    public List<Destinatario> getTargetDest() {
        return targetDest;
    }

    public void setTargetDest(List<Destinatario> targetDest) {
        this.targetDest = targetDest;
    }

    public DualListModel<Destinatario> getDestDualListModel() {
        return destDualListModel;
    }

    public void setDestDualListModel(DualListModel<Destinatario> destDualListModel) {
        this.destDualListModel = destDualListModel;
    }

    public AreaFacade getAreaFacade() {
        return areaFacade;
    }

    public void setAreaFacade(AreaFacade areaFacade) {
        this.areaFacade = areaFacade;
    }

    public List<Area> getSource() {
        return source;
    }

    public void setSource(List<Area> source) {
        this.source = source;
    }

    public List<Area> getTarget() {
        return target;
    }

    public void setTarget(List<Area> target) {
        this.target = target;
    }

    public DualListModel<Area> getAreaDualListModel() {
        return areaDualListModel;
    }

    public void setAreaDualListModel(DualListModel<Area> areaDualListModel) {
        this.areaDualListModel = areaDualListModel;
    }

    @PostConstruct
    public void init() {
        //Areas
        this.source = getAreaFacade().findAll(); // return all my users in database
        this.target = new ArrayList<>();
        //Destinatarios
        this.sourceDest = getDestinatarioFacade().findAll();
        this.targetDest = new ArrayList<>();
        //Disertantes
        this.sourceDisert = getDisertanteFacade().findAll();
        this.targetDisert = new ArrayList<>();
        //modelos
        this.areaDualListModel = new DualListModel<>(this.source, this.target);
        this.destDualListModel = new DualListModel<>(this.sourceDest, this.targetDest);
        this.disertDualListModel = new DualListModel<>(this.sourceDisert, this.targetDisert);

    }

    public PickListView() {
    }

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((Area) item).getNombre()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Item transferido");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onTransferDest(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((Destinatario) item).getDescripcion()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Item transferido");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onTransferDisert(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((Disertante) item).getPersona()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Item transferido");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

    public void limpiar() {

        source = new ArrayList<>();
        target = new ArrayList<>();
        this.areaDualListModel = new DualListModel<>();

    }
}

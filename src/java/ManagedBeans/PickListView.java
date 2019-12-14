/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import Entidades.capacitacion.Area;
import Facades.AreaFacade;
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
@RequestScoped
public class PickListView {

    /**
     * Creates a new instance of PickListView
     */
    @EJB
    private AreaFacade areaFacade;
    
    private List<Area> source;
    private List<Area> target;
    private DualListModel<Area> areaDualListModel;

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

        this.areaDualListModel = new DualListModel<>(this.source, this.target);
        

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

}

package ManagedBeans;

import Entidades.capacitacion.Area;
import Entidades.capacitacion.Capacitacion;
import ManagedBeans.util.JsfUtil;
import ManagedBeans.util.JsfUtil.PersistAction;
import Facades.CapacitacionFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;

@Named("capacitacionController")
@SessionScoped
public class CapacitacionController implements Serializable {

    @EJB
    private Facades.CapacitacionFacade ejbFacade;
    private List<Capacitacion> items = null;
    private List<Capacitacion> itemsHabilitados = null;
    private Capacitacion selected;
    private List<String> listaFechas;
    private List<String> listaAreas;
    private List<String> listaString;
    private DualListModel<String> listaPickAreas;
    @Inject
    private PickListView pickListView;
    private boolean botonHabilitado;

    public boolean isBotonHabilitado() {
        return botonHabilitado;
    }

    public void setBotonHabilitado(boolean botonHabilitado) {
        this.botonHabilitado = botonHabilitado;
    }

    public CapacitacionController() {
    }

    public PickListView getPickListView() {
        return pickListView;
    }

    public void setPickListView(PickListView pickListView) {
        this.pickListView = pickListView;
    }

    public Capacitacion getSelected() {
        return selected;
    }

    public void setSelected(Capacitacion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CapacitacionFacade getFacade() {
        return ejbFacade;
    }

    public List<Capacitacion> getItemsHabilitados() {
        return itemsHabilitados;
    }

    public void setItemsHabilitados(List<Capacitacion> itemsHabilitados) {
        this.itemsHabilitados = itemsHabilitados;
    }

    public Capacitacion prepareCreate() {
        selected = new Capacitacion();
        this.getPickListView().init();
        this.setBotonHabilitado(false);
        initializeEmbeddableKey();
        return selected;
    }
    public void prepararHabilitacion(Capacitacion capa){
        selected=capa;
    }
    public void create() {
        //asociamos los picklist cargados
        selected.setAreas(pickListView.getAreaDualListModel().getTarget());
        selected.setDestinatarios(pickListView.getDestDualListModel().getTarget());
        selected.setDisertantes(pickListView.getDisertDualListModel().getTarget());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CapacitacionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        //asociamos los picklist cargados
        selected.setAreas(pickListView.getAreaDualListModel().getTarget());
        selected.setDestinatarios(pickListView.getDestDualListModel().getTarget());
        selected.setDisertantes(pickListView.getDisertDualListModel().getTarget());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CapacitacionUpdated"));
    }

    public void modificarHabilitacion() {
        //asociamos los datos de la habilitacion
        System.err.println("Habilitada?: "+selected.getHabilitada());
        //selected.setHabilitada(botonHabilitado);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CapacitacionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CapacitacionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Capacitacion> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Capacitacion getCapacitacion(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Capacitacion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Capacitacion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<String> getListaFechas() {
        return listaFechas;
    }

    public void setListaFechas(List<String> listaFechas) {
        this.listaFechas = listaFechas;
    }

    public DualListModel<String> getListaPickAreas() {
        return listaPickAreas;
    }

    public void setListaPickAreas(DualListModel<String> listaPickAreas) {
        this.listaPickAreas = listaPickAreas;
    }

    public List<String> getListaAreas() {
        return listaAreas;
    }

    public void setListaAreas(List<String> listaAreas) {
        this.listaAreas = listaAreas;
    }

    @FacesConverter(forClass = Capacitacion.class)
    public static class CapacitacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CapacitacionController controller = (CapacitacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "capacitacionController");
            return controller.getCapacitacion(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Capacitacion) {
                Capacitacion o = (Capacitacion) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Capacitacion.class.getName()});
                return null;
            }
        }

    }

    public List<String> convertirListaFechas(List<Date> listaFechaDate) {
        this.listaFechas = new ArrayList<>();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        for (Date date : listaFechaDate) {
            this.listaFechas.add(formatoFecha.format(date) + " ");
        }
        System.out.println("lista de fechas date" + listaFechas);
        return this.listaFechas;
    }

    public List<String> convertirLista(List<Object> lista) {
        this.listaString = new ArrayList<>();
        for (Object o : lista) {
            this.listaString.add(o.toString());
        }
        return this.listaString;
    }

    public String formatearFecha(Date fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatoFecha.format(fecha);
        } catch (Exception e) {
            return "";
        }
    }

    public void habilitarBoton(boolean estado) {
        this.setBotonHabilitado(estado);
    }

    public List<Capacitacion> getCapacitacionesHabilitadas() {

        itemsHabilitados = getFacade().getHabilitadas();
        return itemsHabilitados;
    }
}

package ManagedBeans;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("capacitacionController")
@SessionScoped
public class CapacitacionController implements Serializable {

    @EJB
    private Facades.CapacitacionFacade ejbFacade;
    private List<Capacitacion> items = null;
    private Capacitacion selected;
    private List<String> listaFechas;

    public CapacitacionController() {
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

    public Capacitacion prepareCreate() {
        selected = new Capacitacion();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CapacitacionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
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
        SimpleDateFormat formatoFecha= new SimpleDateFormat("dd/MM/yyyy");
        for (Date date : listaFechaDate) {
            this.listaFechas.add(formatoFecha.format(date) + " ");
        }
        System.out.println("lista de fechas date"+ listaFechas);
        return this.listaFechas;
    }
}

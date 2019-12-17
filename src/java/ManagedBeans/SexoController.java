package ManagedBeans;

import Entidades.persona.Sexo;
import Entidades.persona.TipoDocumento;
import ManagedBeans.util.JsfUtil;
import ManagedBeans.util.JsfUtil.PersistAction;
import Facades.TipoDocumentoFacade;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
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

@Named("sexoController")
@SessionScoped
public class SexoController implements Serializable {

    @EJB
    private Facades.TipoDocumentoFacade ejbFacade;
    private List<Sexo> items = null;
    private Sexo selected;

    public SexoController() {
    }

  
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoDocumentoFacade getFacade() {
        return ejbFacade;
    }

    public Sexo prepareCreate() {
        
        initializeEmbeddableKey();
        return selected;
    }

 
    public List<Sexo> getItems() {
        if (items == null) {
            items = Arrays.asList(Sexo.values());
        }
        return items;
    }



    public TipoDocumento getTipoDocumento(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<TipoDocumento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Sexo> getItemsAvailableSelectOne() {
        return Arrays.asList(Sexo.values());
    }

    @FacesConverter(forClass = TipoDocumento.class)
    public static class TipoDocumentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SexoController controller = (SexoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sexoController");
            return controller.getTipoDocumento(getKey(value));
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
            if (object instanceof TipoDocumento) {
                TipoDocumento o = (TipoDocumento) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoDocumento.class.getName()});
                return null;
            }
        }

    }

}

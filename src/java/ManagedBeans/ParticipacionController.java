package ManagedBeans;

import Entidades.capacitacion.Capacitacion;
import Entidades.capacitacion.Participacion;
import Entidades.capacitacion.Participante;
import Entidades.persona.CorreoElectronico;
import Entidades.persona.DocumentoIdentidad;
import Entidades.persona.Persona;
import Entidades.persona.TipoDocumento;
import ManagedBeans.util.JsfUtil;
import ManagedBeans.util.JsfUtil.PersistAction;
import Facades.ParticipacionFacade;
import Facades.ParticipanteFacade;
import RN.ParticipacionRNLocal;
import RN.ParticipanteRNLocal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

@Named("participacionController")
@SessionScoped
public class ParticipacionController implements Serializable {

    @EJB
    private Facades.ParticipacionFacade ejbFacade;
    private List<Participacion> items = null;
    private Participacion selected;
    @EJB
    private ParticipacionRNLocal participacionRNLocal;
    @Inject
    private CapacitacionController capacitacionController;
    @Inject
    private CheckBoxView checkBoxView;
    private TipoDocumento tipoDocumento;
    private Long textoBusquedaDNI;
    private String textoDNI;
    private Participante participante;
    @EJB
    private ParticipanteRNLocal participanteRNLocal;
    private Persona persona;
    private DocumentoIdentidad documentoIdentidad;
    @EJB
    private ParticipanteFacade participanteFacade;
    private CorreoElectronico correoElectronico;

    public ParticipacionController() {
    }

    public CheckBoxView getCheckBoxView() {
        return checkBoxView;
    }

    public void setCheckBoxView(CheckBoxView checkBoxView) {
        this.checkBoxView = checkBoxView;
    }

    public Participacion getSelected() {
        return selected;
    }

    public void setSelected(Participacion selected) {
        this.selected = selected;
    }

    public CapacitacionController getCapacitacionController() {
        return capacitacionController;
    }

    public void setCapacitacionController(CapacitacionController capacitacionController) {
        this.capacitacionController = capacitacionController;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ParticipacionFacade getFacade() {
        return ejbFacade;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Long getTextoBusquedaDNI() {
        return textoBusquedaDNI;
    }

    public void setTextoBusquedaDNI(Long textoBusquedaDNI) {
        this.textoBusquedaDNI = textoBusquedaDNI;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public String getTextoDNI() {
        return textoDNI;
    }

    public void setTextoDNI(String textoDNI) {
        this.textoDNI = textoDNI;
    }

    public Participacion prepareCreate() {
        selected = new Participacion();
        selected.setCapacitacion(capacitacionController.getSelected());
        initializeEmbeddableKey();
        return selected;
    }

    public Participacion prepararIncripcion(Capacitacion capacitacion) {
        textoBusquedaDNI = null;
        textoDNI = "";
        selected = new Participacion();
        selected.setCapacitacion(capacitacion);
        //this.tipoDocumento = new TipoDocumento();
        documentoIdentidad = new DocumentoIdentidad();
        correoElectronico = new CorreoElectronico();
        correoElectronico.setDireccion(" ");
        //documentoIdentidad.setTipoDocumento(tipoDocumento);
        //documentoIdentidad.setNumero(0);

        participante = new Participante();
        persona = new Persona();
        persona.setDocumentoIdentidad(documentoIdentidad);
        persona.setCorreosElectronico(correoElectronico);
        persona.setNombre(" ");
        persona.setApellido(" ");
        participante.setPersona(persona);
        selected.setParticipante(participante);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
//        if (validarInscripcion()) {
            selected.setFechaInscripcion(new Date());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ParticipacionCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
//            selected = null;
//            participante = null;
//        } else {
//
//        }

    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ParticipacionUpdated"));
    }

    public void modificarFechasAsistencias() {
        System.out.println("Fechas Seleccionadas" + checkBoxView.getSelectedFechas());
        selected.setFechaAsistencias(checkBoxView.getSelectedFechas());

        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ParticipacionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ParticipacionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Participacion> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void cargarParticipaciones(Capacitacion capacitacion) {
        //getFacade().
        System.err.println("capacitacion elegida: ---" + capacitacion);
        items = participacionRNLocal.buscarPartDeCapacitacion(capacitacion);
        System.err.println("lista busqueda: -- " + items);
    }

    public void buscarPersona() {
        System.out.println("entro a buscar participante con dni= " + this.getTextoDNI());

        if (!this.getTextoDNI().isEmpty()) {
            Long dni = Long.parseLong(this.getTextoDNI());
            System.out.println("DNI convertido a LONG dni= " + dni);
            participante = participanteFacade.buscarParticipante(dni);
            if (participante.getPersona() != null) {
                selected.setParticipante(participante);
                System.out.println("PARTICIPANTE: " + participante.getPersona().getNombre());

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Advertencia", "No se encontro la persona, de cargar en forma manual los datos"));
                //PrimeFaces.current().ajax().update(":InscripcionCreateForm:itNombre");
            }

            //PrimeFaces.current().ajax().update(":InscripcionCreateForm:itNombre");
            //FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":itNombre"); 
        } else {
            System.out.println(" entro if por el else: " + this.getTextoDNI());
        }

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

    public Participacion getParticipacion(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Participacion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Participacion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    private boolean validarInscripcion() {
        if (getFacade().buscarParticipacion(selected) == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "El Participante ya se encuentra inscripto"));
            return false;
        }
        return true;
    }

    @FacesConverter(forClass = Participacion.class)
    public static class ParticipacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParticipacionController controller = (ParticipacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "participacionController");
            return controller.getParticipacion(getKey(value));
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
            if (object instanceof Participacion) {
                Participacion o = (Participacion) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Participacion.class.getName()});
                return null;
            }
        }

    }

}

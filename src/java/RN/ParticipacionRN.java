/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.capacitacion.Capacitacion;
import Entidades.capacitacion.Participacion;
import Facades.ParticipacionFacade;
import Facades.ProvinciaFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hugo
 */
@Stateless
public class ParticipacionRN implements ParticipacionRNLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "ProyectoCapacitacionPU")
    private EntityManager em;
    @EJB
    private ParticipacionFacade participacionFacade;

    @Override
    public void create(Participacion participacion) throws Exception {
        //Agregar validaciones
        participacionFacade.create(participacion);
    }

    @Override
    public void edit(Participacion participacion) throws Exception {
        participacionFacade.edit(participacion);
    }

    @Override
    public void remove(Participacion participacion) throws Exception {
        participacionFacade.remove(participacion);
    }

    @Override
    public List<Participacion> findAll() throws Exception {
        return participacionFacade.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Participacion buscarParticipacion(Participacion participacion) {
        return participacionFacade.find(participacion.getId());
    }

    @Override
    public List<Participacion> buscarPartDeCapacitacion(Capacitacion capacitacion) {
        Query q = null;
        q = em.createNamedQuery("Participacion.buscarPartDeCapacitacion");
        q.setParameter("capacitacion", capacitacion);
        return q.getResultList();
    }
}

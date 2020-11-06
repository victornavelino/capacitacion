/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.capacitacion.Participante;
import Facades.ParticipacionFacade;
import Facades.ParticipanteFacade;
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
public class ParticipanteRN implements ParticipanteRNLocal {

    @PersistenceContext(unitName = "ProyectoCapacitacionPU")
    private EntityManager em;
    @EJB
    private ParticipanteFacade participanteFacade;

    @Override
    public void create(Participante participante) throws Exception {
        participanteFacade.create(participante);
    }

    @Override
    public void edit(Participante participante) throws Exception {
        participanteFacade.edit(participante);
    }

    @Override
    public void remove(Participante participante) throws Exception {
        participanteFacade.remove(participante);
    }

    @Override
    public List<Participante> findAll() throws Exception {

        return participanteFacade.findAll();
    }

    @Override
    public Participante buscarParticipante(Long dni) {
        Query q = null;
        q = em.createNamedQuery("Participante.buscarParticipante");
        q.setParameter("dni", dni);
        if (q.getSingleResult() != null) {
            return (Participante) q.getSingleResult();
        } else {
            return new Participante();
        }

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

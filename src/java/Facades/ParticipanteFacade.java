/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import Entidades.capacitacion.Participante;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author nago
 */
@Stateless
public class ParticipanteFacade extends AbstractFacade<Participante> {

    @PersistenceContext(unitName = "ProyectoCapacitacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipanteFacade() {
        super(Participante.class);
    }

    public Participante buscarParticipante(Long dni) {
        try {
            Query q = em.createNamedQuery("Participante.buscarParticipante");
            q.setParameter("dni", dni);
            System.out.println("RN.ParticipanteRN.buscarParticipante(): " + q.getResultList());
            return (Participante) q.getSingleResult();
        } catch (Exception e) {
            return new Participante();
        }

    }
}

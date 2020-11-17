/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import Entidades.capacitacion.Participacion;
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
public class ParticipacionFacade extends AbstractFacade<Participacion> {

    @PersistenceContext(unitName = "ProyectoCapacitacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipacionFacade() {
        super(Participacion.class);
    }

    public Participacion buscarParticipacion(Participacion participacion) {
        try {
            System.out.println("BUCAR PARTICIPACION CAPA: "+participacion.getCapacitacion());
            System.out.println("BUCAR PARTICIPACION PARTICIPANTE: "+participacion.getParticipante());
            Query q = em.createNamedQuery("Participacion.buscarParticipacion");
            q.setParameter("capacitacion", participacion.getCapacitacion());
            q.setParameter("participante", participacion.getParticipante());
            return (Participacion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
}

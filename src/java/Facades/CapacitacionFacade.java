/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import Entidades.capacitacion.Capacitacion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author nago
 */
@Stateless
public class CapacitacionFacade extends AbstractFacade<Capacitacion> {

    @PersistenceContext(unitName = "ProyectoCapacitacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CapacitacionFacade() {
        super(Capacitacion.class);
    }

    public List<Capacitacion> getHabilitadas() {
        try {
            Date hoy=new Date();
            Query q = em.createNamedQuery("Capacitacion.getHabilitadas");
            
            q.setParameter("fechaHoy", hoy);
           return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import Entidades.capacitacion.Destinatario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nago
 */
@Stateless
public class DestinatarioFacade extends AbstractFacade<Destinatario> {

    @PersistenceContext(unitName = "ProyectoCapacitacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DestinatarioFacade() {
        super(Destinatario.class);
    }
    
}

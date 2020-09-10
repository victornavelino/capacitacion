/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.capacitacion.Capacitacion;
import Entidades.capacitacion.Participacion;
import Entidades.localidad.Provincia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hugo
 */
@Local
public interface ParticipacionRNLocal {

    public void create(Participacion participacion) throws Exception;

    public void edit(Participacion participacion) throws Exception;

    public void remove(Participacion participacion) throws Exception;

    public List<Participacion> findAll() throws Exception;

    public Participacion buscarParticipacion(Participacion participacion);
    public List<Participacion> buscarPartDeCapacitacion(Capacitacion capacitacion);

}

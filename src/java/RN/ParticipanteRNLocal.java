/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.capacitacion.Participante;
import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author hugo
 */
@Local
public interface ParticipanteRNLocal {

    public void create(Participante participante) throws Exception;

    public void edit(Participante participante) throws Exception;

    public void remove(Participante participante) throws Exception;

    public List<Participante> findAll() throws Exception;

 }

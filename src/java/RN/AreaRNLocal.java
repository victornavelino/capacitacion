/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.capacitacion.Area;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author nago
 */
@Local
public interface AreaRNLocal {
    
    void create(Area area);

    void edit(Area area);

    void remove(Area area);

    Area find(Object id);

    List<Area> findAll();

    List<Area> findRange(int[] range);
    
}

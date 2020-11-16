/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.capacitacion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author hugo
 */

@NamedQueries({
    @NamedQuery(name = "Capacitacion.getHabilitadas", query = "SELECT c FROM Capacitacion c WHERE c.habilitada=True and :fechaHoy <= c.cierre"),
    })
@Entity
@Table(name = "capacitacion")
public class Capacitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String titulo;
    @Lob
    private String objetivo;
    @Lob
    private String informacionAlPublico;
    private int anio;
    private List<Date> fechas;
    private int cantidadMinimaAsistencia;
    private int cantidadHoras;
    private String lugar;
    @OneToMany
    private List<Area> areas;
    @OneToMany
    private List<Destinatario> destinatarios;
    @OneToMany
    private List<Disertante> disertantes;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date apertura;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date cierre;
    private Boolean habilitada;
    @Lob
    private String observaciones;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getInformacionAlPublico() {
        return informacionAlPublico;
    }

    public void setInformacionAlPublico(String informacionAlPublico) {
        this.informacionAlPublico = informacionAlPublico;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<Date> getFechas() {
        return fechas;
    }

    public void setFechas(List<Date> fechas) {
        this.fechas = fechas;
    }

    public int getCantidadMinimaAsistencia() {
        return cantidadMinimaAsistencia;
    }

    public void setCantidadMinimaAsistencia(int cantidadMinimaAsistencia) {
        this.cantidadMinimaAsistencia = cantidadMinimaAsistencia;
    }

    public int getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(int cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public List<Destinatario> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<Destinatario> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public List<Disertante> getDisertantes() {
        return disertantes;
    }

    public void setDisertantes(List<Disertante> disertantes) {
        this.disertantes = disertantes;
    }

    public Date getApertura() {
        return apertura;
    }

    public void setApertura(Date apertura) {
        this.apertura = apertura;
    }

    public Date getCierre() {
        return cierre;
    }

    public void setCierre(Date cierre) {
        this.cierre = cierre;
    }

    public Boolean getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(Boolean habilitada) {
        this.habilitada = habilitada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Capacitacion)) {
            return false;
        }
        Capacitacion other = (Capacitacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "capacitacion.Capacitacion[ id=" + id + " ]";
    }

}

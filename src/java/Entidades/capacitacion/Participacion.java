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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author hugo
 */
@Entity
@Table(name="participacion")
public class Participacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Capacitacion capacitacion;
    @OneToOne
    private Participante participante;
    @OneToOne
    private Cargo cargo;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInscripcion;
    private List<Date> fechaAsistencias;
    @OneToOne
    private Resultado resultado;
    @OneToOne
    private Area dependencia;
    @OneToOne
    private Certificado certificado;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Capacitacion getCapacitacion() {
        return capacitacion;
    }

    public void setCapacitacion(Capacitacion capacitacion) {
        this.capacitacion = capacitacion;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public List<Date> getFechaAsistencias() {
        return fechaAsistencias;
    }

    public void setFechaAsistencias(List<Date> fechaAsistencias) {
        this.fechaAsistencias = fechaAsistencias;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public Area getDependencia() {
        return dependencia;
    }

    public void setDependencia(Area dependencia) {
        this.dependencia = dependencia;
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
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
        if (!(object instanceof Participacion)) {
            return false;
        }
        Participacion other = (Participacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "capacitacion.Inscripcion[ id=" + id + " ]";
    }
    
}

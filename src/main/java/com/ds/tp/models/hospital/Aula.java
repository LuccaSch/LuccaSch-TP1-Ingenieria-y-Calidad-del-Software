package com.ds.tp.models.aula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "aula")
@Inheritance(strategy = InheritanceType.JOINED)
public class Aula {

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "maximo_alumnos",nullable=false)
    protected Integer maximoAlumnos;

    @Column(name = "aula_disponible", nullable=false)
    protected boolean aulaDisponible;

    @Column(name = "piso")
    protected String piso;

    @Column(name = "tipo_pizzarron")
    protected String tipoPizarron;

    @Column(name = "aire_acondicionado")
    protected Boolean aireAcondicionado;

    public Aula(){}

    public Aula(Long id, Integer maximoAlumnos, String piso, String tipoPizarron, boolean aulaDisponible) {
        this.aulaDisponible = aulaDisponible;
        this.id = id;
        this.maximoAlumnos = maximoAlumnos;
        this.piso = piso;
        this.tipoPizarron = tipoPizarron;
    }
    
    // Getters y Setters

    public Long getIdAula() {
        return id;
    }
    
    public void setIdAula(Long id) {
        this.id = id;
    }

    public Integer getMaximoAlumnos() {
        return maximoAlumnos;
    }

    public void setMaximoAlumnos(Integer maximoAlumnos) {
        this.maximoAlumnos = maximoAlumnos;
    }

    public boolean isDisponible() {
        return aulaDisponible;
    }

    public void setAulaDisponible(Boolean aulaDisponible) {
        this.aulaDisponible = aulaDisponible;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getTipoPizarron() {
        return tipoPizarron;
    }

    public void setTipoPizarron(String tipoPizaarron) {
        this.tipoPizarron = tipoPizaarron;
    }

    public Boolean isAireAcondicionado() {
        return aireAcondicionado;
    }

    public void setAireAcondicionado(Boolean aireAcondicionado) {
        this.aireAcondicionado = aireAcondicionado;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; 
        }
    
        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }
    
        Aula other = (Aula) obj;
    
        return this.id != null && this.id.equals(other.getIdAula());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
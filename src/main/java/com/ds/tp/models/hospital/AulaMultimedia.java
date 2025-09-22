package com.ds.tp.models.aula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "aula_multimedia")
@PrimaryKeyJoinColumn(name = "id") 
public class AulaMultimedia extends Aula{
    /*
    atributos heredados:

    Long id;
    int maximoAlumnos;
    boolean estado;
    String piso;
    String tipoPizaarron;
    boolean aireAcondicionado;
    */

    //atributos

    @Column
    private Boolean televisor;

    @Column
    private Boolean canion;

    @Column
    private Boolean computadora;

    @Column
    private Boolean ventiladores;

    //constructores

    //constructor: por defecto
    public AulaMultimedia(){}

    //constructor: todos los campos
    public AulaMultimedia(Integer maximoAlumnos, Boolean estado, String piso, String tipoPizaarron, Boolean aireAcondicionado,Boolean televisor,Boolean canion,Boolean computadora, Boolean ventiladores){
        this.maximoAlumnos = maximoAlumnos;
        this.aulaDisponible = estado;
        this.piso = piso;
        this.tipoPizarron = tipoPizaarron;
        this.aireAcondicionado = aireAcondicionado;
        this.televisor=televisor;
        this.canion=canion;
        this.computadora=computadora;
        this.ventiladores=ventiladores;
    }

    //constructor: Solo los campos que no pueden ser null
    public AulaMultimedia(Integer maximoAlumnos, Boolean estado){
        this.maximoAlumnos = maximoAlumnos;
        this.aulaDisponible = estado;
    }

    public Boolean isTelevisor() {
        return televisor;
    }
    public void setTelevisor(Boolean televisor) {
        this.televisor = televisor;
    }

    public Boolean isCanion() {
        return canion;
    }
    public void setCanion(Boolean canion) {
        this.canion = canion;
    }

    public Boolean isComputadora() {
        return computadora;
    }
    public void setComputadora(Boolean computadora) {
        this.computadora = computadora;
    }

    public Boolean isVentiladores() {
        return ventiladores;
    }
    public void setVentiladores(Boolean ventiladores) {
        this.ventiladores = ventiladores;
    }
}

package com.ds.tp.models.aula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="aula_sin_recursos")
@PrimaryKeyJoinColumn(name = "id") 
public class AulaSinRecursos extends Aula{

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
    protected Boolean ventiladores;
    
    @Column
    protected String descripcion;

    //constructor

    //constructor: por defecto
    public AulaSinRecursos(){}

    //constructor: todos los campos
    public AulaSinRecursos(Integer maximoAlumnos, Boolean estado, String piso, String tipoPizaarron, Boolean aireAcondicionado,Boolean ventiladores,String descripcion){
        this.maximoAlumnos = maximoAlumnos;
        this.aulaDisponible = estado;
        this.piso = piso;
        this.tipoPizarron = tipoPizaarron;
        this.aireAcondicionado = aireAcondicionado;
        this.ventiladores=ventiladores;
        this.descripcion=descripcion;    
    }

    //constructor: Solo los campos que no pueden ser null
    public AulaSinRecursos(Integer maximoAlumnos, Boolean estado){
        this.maximoAlumnos = maximoAlumnos;
        this.aulaDisponible = estado;
    }

    //getter/setter
    public Boolean isVentiladores() {
        return ventiladores;
    }
    public void setVentiladores(Boolean ventiladores) {
        this.ventiladores = ventiladores;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

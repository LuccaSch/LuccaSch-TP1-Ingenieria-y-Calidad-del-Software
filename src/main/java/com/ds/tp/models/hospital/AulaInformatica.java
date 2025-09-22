package com.ds.tp.models.aula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "aula_informatica")
@PrimaryKeyJoinColumn(name = "id")  
public class AulaInformatica extends Aula{
    
    /*
    atributos heredados:

    int id;
    int maximoAlumnos;
    boolean estado;
    String piso;
    String tipoPizaarron;
    boolean aireAcondicionado;
    */
    
    //atributos

    @Column(name="cant_pc", nullable=false)
    private Integer cantPc;

    @Column
    private Boolean canion; 

    //constructores

    //constructor: por defecto
    public AulaInformatica(){}

    //constructor: todos los campos
    public AulaInformatica(Integer maximoAlumnos, Boolean estado, String piso, String tipoPizaarron, Boolean aireAcondicionado,Integer cantPc,Boolean canion){
        this.maximoAlumnos = maximoAlumnos;
        this.aulaDisponible = estado;
        this.piso = piso;
        this.tipoPizarron = tipoPizaarron;
        this.aireAcondicionado = aireAcondicionado;
        this.cantPc=cantPc;
        this.canion=canion;
    }

    //constructor: Solo los campos que no pueden ser null
    public AulaInformatica(Integer maximoAlumnos, Boolean estado){
        this.maximoAlumnos = maximoAlumnos;
        this.aulaDisponible = estado;
    }


    //getter/setter
    public Integer getCantPc() {
        return cantPc;
    }
    public void setCantPc(Integer cantPc) {
        this.cantPc = cantPc;
    }

    public Boolean isCanion() {
        return canion;
    }
    public void setCanion(Boolean canion) {
        this.canion = canion;
    }
}

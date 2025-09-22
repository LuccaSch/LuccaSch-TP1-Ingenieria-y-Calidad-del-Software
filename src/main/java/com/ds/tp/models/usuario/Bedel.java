package com.ds.tp.models.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_bedel")
public class Bedel extends Usuario{

    /* 
    atributos heredados:
    
    Long id;
    String usuario;
    String nombre;
    String apellido;
    String contrasenia;

    */

    //atributos

    @Column
    private Integer turno;
    @Column(nullable=false)
    private Boolean estado;

    //constructor

    //constructor: por defecto
    public Bedel(){}

    //constructor: todos los campos
    public Bedel(String usuario, String nombre, String apellido, String contrasenia,Integer turno,Boolean estado){
        this.usuario=usuario;
        this.nombre=nombre;
        this.contrasenia=contrasenia;
        this.apellido=apellido;
        this.turno=turno;
        this.estado=estado;
    }

    //constructor: Solo los campos que no pueden ser null
    public Bedel(String usuario,String contrasenia,Integer turno,Boolean estado){
        this.usuario=usuario;
        this.contrasenia=contrasenia;
        this.turno=turno;
        this.estado=estado;
    }

    //getter/setter
    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public String getTurnoString(){
        return String.valueOf(this.turno);
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    //otros metodos

    @Override
    public String toString(){
        return "BEDEL {Id: " + id +
                    ", Usuario: "+usuario+
                    ", Nombre: " + nombre + 
                    ", Apellido "+apellido+
                    "}";
    }
}

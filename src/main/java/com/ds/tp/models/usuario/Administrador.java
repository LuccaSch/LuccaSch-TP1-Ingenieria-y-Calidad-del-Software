package com.ds.tp.models.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="usuario_administrador")
public class Administrador extends Usuario{
    /* 
    atributos heredados:
    
    Long id;
    String usuario;
    String nombre;
    String apellido;
    String contrasenia;

    */

    //En el futuro se le pueden agregar atributos
    
    //constructor
        
    //constructor: por defecto
    public Administrador(){}

    //constructor: todos los campos
    public Administrador(String usuario, String nombre, String apellido, String contrasenia){
        this.usuario=usuario;
        this.nombre=nombre;
        this.contrasenia=contrasenia;
        this.apellido=apellido;
    }
}

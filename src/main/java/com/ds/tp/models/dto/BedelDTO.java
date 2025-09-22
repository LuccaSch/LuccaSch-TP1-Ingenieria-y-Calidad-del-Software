package com.ds.tp.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BedelDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("apellido")
    private String apellido;

    @JsonProperty("usuario")
    private String usuario;

    @JsonProperty("contrasenia")
    private String contrasenia;

    @JsonProperty("confContrasenia")
    private String confContrasenia;

    @JsonProperty("email")
    private String email;

    @JsonProperty("turno")
    private Integer turno;

    @JsonProperty("estado")
    private Boolean estado;

    //constructor

    public BedelDTO(Long id,String usuario, String nombre,String apellido, Integer turno,Boolean estado) {
        this.id=id;
        this.apellido = apellido;
        this.nombre = nombre;
        this.turno = turno;
        this.usuario = usuario;
        this.estado = estado;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public String getConfContrasenia() {
        return confContrasenia;
    }

    public void setConfContrasenia(String confContrasenia) {
        this.confContrasenia = confContrasenia;
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
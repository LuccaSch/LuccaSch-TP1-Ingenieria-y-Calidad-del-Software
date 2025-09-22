package com.ds.tp.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AulaDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("maximoAlumnos")
    private Integer maximoAlumnos;

    @JsonProperty("aulaDisponible")
    private Boolean aulaDisponible;

    @JsonProperty("piso")
    private String piso;

    @JsonProperty("tipoPizarron")
    private String tipoPizarron;

    public AulaDTO(Long id,Boolean aulaDisponible, Integer maximoAlumnos, String piso, String tipoPizarron) {
        this.aulaDisponible = aulaDisponible;
        this.id = id;
        this.maximoAlumnos = maximoAlumnos;
        this.piso = piso;
        this.tipoPizarron = tipoPizarron;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaximoAlumnos() {
        return maximoAlumnos;
    }

    public void setMaximoAlumnos(Integer maximoAlumnos) {
        this.maximoAlumnos = maximoAlumnos;
    }

    public Boolean getAulaDisponible() {
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

    public void setTipoPizarron(String tipoPizarron) {
        this.tipoPizarron = tipoPizarron;
    }
}

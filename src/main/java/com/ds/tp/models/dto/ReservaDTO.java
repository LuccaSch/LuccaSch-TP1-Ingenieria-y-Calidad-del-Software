package com.ds.tp.models.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservaDTO{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("idDocente")
    private Long idDocente;

    @JsonProperty("idAsignatura")
    private Long idAsignatura;

    @JsonProperty("idBedel")
    private Long idBedel;

    @JsonProperty("usuarioBedel")
    private String usuarioBedel;
    
    @JsonProperty("nombreDocente")
    private String nombreDocente;
    
    @JsonProperty("nombreAsignatura")
    private String nombreAsignatura;
    
    @JsonProperty("cantAlumnos")
    private Integer cantAlumnos;

    @JsonProperty("fechaRegistro")
    private Timestamp fechaRegistroTimestamp;

    @JsonProperty("listaDiasReservaDTO")
    private List<DiaReservaDTO> listaDiasReservaDTO;

    @JsonProperty("tipo")
    // 0 periodica, 1 esporadica
    private int tipo;

    @JsonProperty("periodo")
    //0 Anual, 1 primerCuatrimestr, 2 SegundoCuatrimestre 
    private Long periodo;

    public ReservaDTO() {}

    public ReservaDTO(Long id,Integer cantAlumnos, Timestamp fechaRegistroTimestamp, Long idAsignatura, String idBedel, Long idDocente, String nombreAsignatura, String nombreDocente,int tipo, List<DiaReservaDTO> listaDiasReservaDTO) {
        this.cantAlumnos = cantAlumnos;
        this.fechaRegistroTimestamp = fechaRegistroTimestamp;
        this.id = id;
        this.idAsignatura = idAsignatura;
        this.usuarioBedel = idBedel;
        this.idDocente = idDocente;
        this.nombreAsignatura = nombreAsignatura;
        this.nombreDocente = nombreDocente;
        this.listaDiasReservaDTO=listaDiasReservaDTO;
        this.tipo=tipo;
    }

    //NO MODIFICAR SE USA PARA DISPONIBILIDAD
    public ReservaDTO(Long id, Long idAsignatura, Long idBedel, Long idDocente, String nombreAsignatura, String nombreDocente) {
        this.id = id;
        this.idAsignatura = idAsignatura;
        this.idBedel = idBedel;
        this.idDocente = idDocente;
        this.nombreAsignatura = nombreAsignatura;
        this.nombreDocente = nombreDocente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public Long getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Long idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public Long getIdBedel() {
        return idBedel;
    }

    public void setIdBedel(Long idBedel) {
        this.idBedel = idBedel;
    }

    public String getUsuarioBedel() {
        return usuarioBedel;
    }

    public void setUsuarioBedel(String usuarioBedel) {
        this.usuarioBedel = usuarioBedel;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public Integer getCantAlumnos() {
        return cantAlumnos;
    }

    public void setCantAlumnos(Integer cantAlumnos) {
        this.cantAlumnos = cantAlumnos;
    }

    public Timestamp getFechaRegistroTimestamp() {
        return fechaRegistroTimestamp;
    }

    public void setFechaRegistroTimestamp(Timestamp fechaRegistroTimestamp) {
        this.fechaRegistroTimestamp = fechaRegistroTimestamp;
    }

    public List<DiaReservaDTO> getListaDiasReservaDTO() {
        return listaDiasReservaDTO;
    }

    public void setListaDiasReservaDTO(List<DiaReservaDTO> listaDiasReservaDTO) {
        this.listaDiasReservaDTO = listaDiasReservaDTO;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "ReservaDTO [idDocente=" + idDocente + ", idAsignatura=" + idAsignatura + ", idBedel=" + idBedel
                + ", usuarioBedel=" + usuarioBedel + ", nombreDocente=" + nombreDocente + ", nombreAsignatura="
                + nombreAsignatura + ", cantAlumnos=" + cantAlumnos + ", fechaRegistroTimestamp="
                + fechaRegistroTimestamp + ", listaDiasReservaDTO=" + listaDiasReservaDTO + ", tipo=" + tipo + "]";
    }
}
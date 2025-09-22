package com.ds.tp.models.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RequerimientoDisponibilidadDTO {

    @JsonProperty("cantidadAlumnos")
    private Integer cantidadAlumnos;

    //esporadica = false, periodica = true
    @JsonProperty("tipoReserva")
    private Boolean tipoReserva;

    // 0 Informatica, 1 Multimedia, 2 SinRecurso
    @JsonProperty("tipoAula")
    int tipoAula;

    // 0 Anual, 1 primer cuatri, 2 segundo cuatri
    @JsonProperty("periodo")
    Long periodo;

    @JsonProperty("diasReserva")
    List<DiaReservaDTO> diasReserva;

    Map<Long,List<DiaReservaDTO>> mapDiasSemana;

    public RequerimientoDisponibilidadDTO(Integer cantidadAlumnos, List<DiaReservaDTO> diasReserva, int tipoAula, Boolean tipoReserva) {
        this.cantidadAlumnos = cantidadAlumnos;
        this.diasReserva = diasReserva;
        this.tipoAula = tipoAula;
        this.tipoReserva = tipoReserva;
    }

    public Integer getCantidadAlumnos() {
        return cantidadAlumnos;
    }

    public void setCantidadAlumnos(Integer cantidadAlumnos) {
        this.cantidadAlumnos = cantidadAlumnos;
    }

    public Boolean getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(Boolean tipoReserva) {
        this.tipoReserva = tipoReserva;
    }

    public int getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(int tipoAula) {
        this.tipoAula = tipoAula;
    }

    public List<DiaReservaDTO> getDiasReserva() {
        return diasReserva;
    }

    public void setDiasReserva(List<DiaReservaDTO> diasReserva) {
        this.diasReserva = diasReserva;
    }

    public Long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }

    public Map<Long, List<DiaReservaDTO>> getMapDiasSemana() {
        return mapDiasSemana;
    }

    public void setMapDiasSemana(Map<Long, List<DiaReservaDTO>> mapDiasSemana) {
        this.mapDiasSemana = mapDiasSemana;
    }

    public void inicializarMapDiaSemana(){
        this.mapDiasSemana=new HashMap<>();
    }

}

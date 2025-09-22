package com.ds.tp.models.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiaReservaDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fechaReserva")
    private LocalDate fechaReserva;

    @JsonProperty("horaInicio")
    private LocalTime horaInicio;

    @JsonProperty("duracion")
    private Integer duracion;
    
    @JsonProperty("idAula")
    private Long idAula;

    @JsonProperty("diaSemana")
    private DayOfWeek diaSemana;

    public DiaReservaDTO() {
    }

    public DiaReservaDTO(Long id,Integer duracion, LocalDate fechaReserva, LocalTime horaInicio, Long idAula) {
        this.duracion = duracion;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.id = id;
        this.idAula = idAula;
    }

    public DiaReservaDTO(Integer duracion, LocalDate fechaReserva, LocalTime horaInicio, Long idAula) {
        this.duracion = duracion;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.idAula = idAula;
    }

    public DiaReservaDTO(Integer duracion, LocalDate fechaReserva, LocalTime horaInicio) {
        this.duracion = duracion;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }
}

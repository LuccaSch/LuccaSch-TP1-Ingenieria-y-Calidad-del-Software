package com.ds.tp.models.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DisponibilidadAulaDTO {

    @JsonProperty("superposicion")
    private boolean superposicion;

    @JsonProperty("aulasCandidatas")
    private List<AulaDTO> listaAulas;

    @JsonProperty("reserva")
    private ReservaDTO reserva;


    //Constructor

    public DisponibilidadAulaDTO(ReservaDTO reserva){
        this.reserva = reserva;
        this.superposicion = true;
    }

    public DisponibilidadAulaDTO(List<AulaDTO> listaAulas) {
        this.listaAulas = listaAulas;
        this.superposicion = false;
    }

    public DisponibilidadAulaDTO(){}

    //GETTER/SETTER

    public boolean isSuperposicion() {
        return superposicion;
    }

    public ReservaDTO getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDTO reserva) {
        this.reserva = reserva;
    }

    public List<AulaDTO> getListaAulas() {
        return listaAulas;
    }

    public void setListaAulas(List<AulaDTO> listaAulas) {
        this.listaAulas = listaAulas;
    }

    @Override
    public String toString() {
        return "DisponibilidadAulaDTO [superposicion=" + superposicion + ", listaAulas=" + listaAulas + ", reserva="
                + reserva + "]";
    }

}

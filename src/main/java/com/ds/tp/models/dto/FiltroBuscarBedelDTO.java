package com.ds.tp.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FiltroBuscarBedelDTO {
    @JsonProperty("filtro")
    private String filtro;

    @JsonProperty("valorBusqueda")
    private String valorBusqueda;

    public String getFiltro() {
        return filtro;
    }

    public String getValorBusqueda() {
        return valorBusqueda;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public void setValorBusqueda(String valorBusqueda) {
        this.valorBusqueda = valorBusqueda;
    }
}

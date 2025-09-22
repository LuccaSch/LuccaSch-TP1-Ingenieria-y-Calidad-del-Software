package com.ds.tp.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfesorDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombre")
    private String nombre;

    public ProfesorDTO(){}
    
    public ProfesorDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}

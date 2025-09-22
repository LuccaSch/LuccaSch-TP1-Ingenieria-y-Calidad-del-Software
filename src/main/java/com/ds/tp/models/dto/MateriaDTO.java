package com.ds.tp.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateriaDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombre")
    private String nombre;

    public MateriaDTO() {
    }

    public MateriaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

}

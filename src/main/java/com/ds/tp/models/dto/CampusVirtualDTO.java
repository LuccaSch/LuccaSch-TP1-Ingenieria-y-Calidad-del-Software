package com.ds.tp.models.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampusVirtualDTO {
    @JsonProperty("materias")
    private List<MateriaDTO> materias;

    @JsonProperty("docentes")
    private List<ProfesorDTO> profesores;

    public CampusVirtualDTO() {
    }

    public CampusVirtualDTO(List<MateriaDTO> materias, List<ProfesorDTO> profesores) {
        this.materias = materias;
        this.profesores = profesores;
    }
}

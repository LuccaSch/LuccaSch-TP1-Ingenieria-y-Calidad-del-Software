package com.ds.tp.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ds.tp.models.dto.CampusVirtualDTO;
import com.ds.tp.models.dto.MateriaDTO;
import com.ds.tp.models.dto.ProfesorDTO;


@Service
public class CampusVirtualUTNService {

    public CampusVirtualDTO getDatosCampus(){
        return new CampusVirtualDTO(getDatosMateria(), getDatosProfesor());
    }

    public List<MateriaDTO> getDatosMateria(){
        
        List<MateriaDTO> materias= new ArrayList<>();

        materias.addAll(Arrays.asList(
                        new MateriaDTO(1L, "Diseño de Sistemas"),
                        new MateriaDTO(2L, "Sistemas Operativos"),
                        new MateriaDTO(3L, "Fisica 2"),
                        new MateriaDTO(4L, "Arquiectura de Computadoras")
        ));

        return materias;
    }


    public List<ProfesorDTO> getDatosProfesor(){

        List<ProfesorDTO> profesores = new ArrayList<>();

        profesores.addAll(Arrays.asList(
                        new ProfesorDTO(100L, "Impini Cristian"),
                        new ProfesorDTO(101L, "Chevalier Alicia"),
                        new ProfesorDTO(102L, "Llorez Roman"),
                        new ProfesorDTO(103L, "Silvina Mainero")
        ));

        return profesores;
    }
}

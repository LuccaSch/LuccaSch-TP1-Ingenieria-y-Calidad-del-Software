package com.ds.tp.services;

import org.springframework.stereotype.Service;

import com.ds.tp.models.dto.RequerimientoContraseniaDTO;

@Service
public class EmpresaService {

    public RequerimientoContraseniaDTO getRequerimientoContrasenia(){
        //Mockup de la respuesta que debe ser sacada de una controladora con peticion GET al servicio de la empresa
        return new RequerimientoContraseniaDTO(6, 1, 1);
    }

    public boolean validarRequerimientoContrasenia(String contrasenia){
        RequerimientoContraseniaDTO reqContrasenia = getRequerimientoContrasenia();
    
        // Validar longitud
        if (contrasenia.length() < reqContrasenia.getCantDigitos()) {
            return false;
        }
        
        // Validar mayúsculas
        if (contrasenia.chars().filter(Character::isUpperCase).count() < reqContrasenia.getCantMayusculas()) {
            return false;
        }

        // Validar números y ya retorna el resultado   

        return contrasenia.chars().filter(Character::isDigit).count() >= reqContrasenia.getCantNumeros();
    }

    
}

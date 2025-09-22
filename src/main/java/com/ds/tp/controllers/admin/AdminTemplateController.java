package com.ds.tp.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminTemplateController {

    public AdminTemplateController(){}

    @GetMapping()
    public String home(){
        return "menuAdministrador";
    }

    @GetMapping("/registrarBedel")
    public String mostrarFormularioRegistro() {
        return "registrarBedel";
    }

    @GetMapping("/buscarBedel")
    public String buscarBedel() {
        return "buscarBedel";
    }

}

package com.ds.tp.controllers.bedel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/bedel")

public class BedelTemplateController {

    //Constructor
    public BedelTemplateController(){}

    //Menu principal
    @GetMapping()
    public String home(){
        return "menuBedel";
    }

    @GetMapping("/reserva/buscar")
    public String misReservas(){
        return "buscarReserva";
    }

    @GetMapping("reserva/crear")
    public String tipoReserva(){
        return "seleccionarTipoReserva";
    }

    @GetMapping("reserva/crear/reservaPeriodica")
    public String reservaPeriodica(){
        return "reservaPeriodica";
    }
    
    @GetMapping("reserva/crear/reservaEsporadica")
    public String reservaEsporadica(){
        return "reservaEsporadica";
    }

    @GetMapping("aula/buscar")
    public String getMethodName() {
        return "buscarAula";
    }
    
}

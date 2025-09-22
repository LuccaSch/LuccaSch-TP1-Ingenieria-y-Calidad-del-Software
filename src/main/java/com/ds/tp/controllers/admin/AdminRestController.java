package com.ds.tp.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.tp.models.dto.BedelDTO;
import com.ds.tp.models.dto.FiltroBuscarBedelDTO;
import com.ds.tp.services.BedelService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/api")
//ENDPOINT para la api rest
public class AdminRestController {
    @Autowired
    private final BedelService bedelService;

    //constructor
    public AdminRestController(BedelService bedelService){
        this.bedelService = bedelService;
    }

    @PostMapping("/getBedel")
    public ResponseEntity<Object> buscarBedel(@RequestBody FiltroBuscarBedelDTO filtroDatos, HttpServletRequest request) {
        String clientIp = "[GET /getBedel] Solicitud de consulta de bedels desde IP: "+request.getRemoteAddr();

        System.out.print(clientIp);

        return this.bedelService.getBedels(filtroDatos);
    }

    @PostMapping("/postBedel")
    public ResponseEntity<Object> registrarBedel(@RequestBody BedelDTO unBedelDTO, HttpServletRequest request) {
        //Sistema de logs, en este caso muestra la ip del cliente que solicita el registro, a futuro debemos almacenarlos en un txt y usar log4j
        String clientIp = "[POST /postBedel] Solicitud de registro de bedel desde IP: "+request.getRemoteAddr();
        
        System.out.print(clientIp);

        return this.bedelService.postBedel(unBedelDTO);
    }

    @PutMapping("/updateBedel")
    public ResponseEntity<Object> actualizarBedel(@RequestBody BedelDTO bedelDTO) {

        return this.bedelService.putBedel(bedelDTO);
    }

    @PutMapping("/deleteBedel/{id}")
    public ResponseEntity<Object> deleteBedel(@PathVariable Long id){
        return bedelService.deleteBedel(id);
    }
    
    @PutMapping("/activarBedel/{id}")
    public ResponseEntity<Object> activateBedel(@PathVariable Long id) {
        return bedelService.activateBedel(id);
    }
}




package com.ds.tp.controllers.bedel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.tp.models.dto.CampusVirtualDTO;
import com.ds.tp.models.dto.RequerimientoDisponibilidadDTO;
import com.ds.tp.models.dto.ReservaDTO;
import com.ds.tp.services.AulaService;
import com.ds.tp.services.CampusVirtualUTNService;
import com.ds.tp.services.ReservaService;



@RestController
@RequestMapping("/bedel/api") 

public class BedelRestController {

    //Atributos
    @Autowired
    AulaService aulaService;

    @Autowired
    ReservaService reservaService;

    @Autowired
    CampusVirtualUTNService campusVirtualUTNService;
    

    //Constructor
    public BedelRestController(AulaService aulaService,ReservaService reservaService, CampusVirtualUTNService campusVirtualUTNService){
        this.aulaService=aulaService;
        this.reservaService=reservaService;
        this.campusVirtualUTNService=campusVirtualUTNService;
    }

    //Metodos

    @PostMapping("/getAula/esporadica")
    public ResponseEntity<Object> buscarAulasEsporadica(@RequestBody RequerimientoDisponibilidadDTO requisito) {
        return aulaService.getAulaEsporadica(requisito); 
    }

    @PostMapping("/getAula/periodica")
    public ResponseEntity<Object> buscarAulasPeriodica(@RequestBody RequerimientoDisponibilidadDTO requisito) {
        return aulaService.getAulaPeriodica(requisito); 
    }
    
    @PostMapping("/reserva/registrar")
    public ResponseEntity<Object> postMethodName(@RequestBody ReservaDTO reservaDTO) {
    
        return reservaService.postReserva(reservaDTO);
    }

    @GetMapping("/getDatosCampus")
    public CampusVirtualDTO getMethodName() {
        return campusVirtualUTNService.getDatosCampus();
    }
    

}

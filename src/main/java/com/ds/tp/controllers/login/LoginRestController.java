package com.ds.tp.controllers.login;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.tp.util.DSUtilResponseEntity;

@RestController
@RequestMapping("/current/api")
public class LoginRestController {

    @GetMapping("/user")
    public ResponseEntity<Object> getCurrentUser(Authentication logeado) {
        if (logeado == null || !logeado.isAuthenticated()) {
            return DSUtilResponseEntity.statusUnauthorized("El usuario no fue autenticado, por favor inicie sesion");
        }
        return DSUtilResponseEntity.statusOk(logeado.getName());
    }

}

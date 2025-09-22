package com.ds.tp.controllers.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginTemplateController {

    public LoginTemplateController(){}

    @GetMapping()
    public String login(){
        return "login";
    }
}

package com.ds.tp.controllers.publico;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
    public class PublicTemplateController {

    @GetMapping
    public String home() {
        return "index";
    }

}

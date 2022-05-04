package com.Grupo02HDP.GestionNotas.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class viewController {

    @GetMapping("home")
    public String index() {
        return "index.html";
    }

}

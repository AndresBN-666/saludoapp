package com.ejemplo.saludoapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class saludoController {

    @GetMapping("/saludo")
    public String saludo(){
        return "Hola desde Spring Boot";
    }
}

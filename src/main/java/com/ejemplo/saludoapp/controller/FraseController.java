package com.ejemplo.saludoapp.controller;

import com.ejemplo.saludoapp.model.Frase;
import com.ejemplo.saludoapp.service.FraseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FraseController {

    public final FraseService fraseService;

    public FraseController(FraseService fraseService) {
        this.fraseService = fraseService;
    }

    @GetMapping("/frase")
    public Frase obtenerFrase(){
        return fraseService.obtenerFraseAleatoria();
    }
}

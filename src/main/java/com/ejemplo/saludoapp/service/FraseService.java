package com.ejemplo.saludoapp.service;

import com.ejemplo.saludoapp.model.Frase;
import org.springframework.stereotype.Service;


public interface FraseService {
    Frase obtenerFraseAleatoria();
}

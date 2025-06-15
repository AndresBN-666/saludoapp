package com.ejemplo.saludoapp.serviceImpl;

import com.ejemplo.saludoapp.model.Frase;
import com.ejemplo.saludoapp.service.FraseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class FraseServiceImpl implements FraseService {

    private final List<Frase> frases = List.of(
            new Frase(1L, "Nuca te rindas"),
            new Frase(2L, "Cree en ti"),
            new Frase(3L, "Hoy es un buen dia para empezar"),
            new Frase(4L, "El esfuerzo tiene recompensas")
    );
    private final Random random = new Random();

    @Override
    public Frase obtenerFraseAleatoria() {
        return frases.get(random.nextInt(frases.size()));
    }
}

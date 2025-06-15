package com.ejemplo.saludoapp.service;

import com.ejemplo.saludoapp.model.Tarea;

import java.util.List;

public interface TareaService {
    List<Tarea> listarTodas();
    List<Tarea> listarCompletadas();
    Tarea guardarTarea(Tarea tarea);
}

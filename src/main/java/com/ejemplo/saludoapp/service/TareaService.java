package com.ejemplo.saludoapp.service;

import com.ejemplo.saludoapp.DTO.tarea.TareaActualizarDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaCreateDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaDTO;
import com.ejemplo.saludoapp.model.Tarea;

import java.util.List;

public interface TareaService {
    List<Tarea> listarTodas();
    List<Tarea> listarCompletadas();
    Tarea guardarTarea(Tarea tarea);
    TareaDTO crearTarea(TareaCreateDTO tarea);

    TareaDTO actualizarTarea(Long id, TareaActualizarDTO tarea);
}

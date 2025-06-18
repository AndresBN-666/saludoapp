package com.ejemplo.saludoapp.service;

import com.ejemplo.saludoapp.DTO.tarea.TareaActualizarDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaCreateDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaDTO;
import com.ejemplo.saludoapp.model.Tarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TareaService {
    List<Tarea> listarTodas();
    List<Tarea> listarCompletadas();
    Tarea guardarTarea(Tarea tarea);
    TareaDTO crearTarea(TareaCreateDTO tarea);

    TareaDTO actualizarTarea(Long id, TareaActualizarDTO tarea);
    void eliminarTarea(Long id);

    Page<TareaDTO> listarTeares(String titulo, Boolean completada, Long usuarioId, Pageable pageable);
}

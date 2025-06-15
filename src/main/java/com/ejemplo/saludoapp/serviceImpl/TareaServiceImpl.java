package com.ejemplo.saludoapp.serviceImpl;

import com.ejemplo.saludoapp.model.Tarea;
import com.ejemplo.saludoapp.repository.TareaRepository;
import com.ejemplo.saludoapp.service.TareaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl implements TareaService {

/*    private final List<Tarea> tareas = List.of(
            new Tarea(1L, "Estudiar Spring", false),
            new Tarea(2L, "Leer un libro", true),
            new Tarea(3L, "Hacer ejercicio", false)
    );*/

    private final TareaRepository tareaRepository;

    public TareaServiceImpl(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }


    @Override
    public List<Tarea> listarTodas() {
        return tareaRepository.findAll();
    }

    @Override
    public List<Tarea> listarCompletadas() {
        return tareaRepository.findByCompletadaTrue();
    }

    @Override
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }
}

package com.ejemplo.saludoapp.controller;

import com.ejemplo.saludoapp.model.Tarea;
import com.ejemplo.saludoapp.service.TareaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarea")
public class TareaController {
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public List<Tarea> obtenerTareas() {
        return tareaService.listarTodas();
    }

    @GetMapping("/completadas")
    public List<Tarea> tareasCompletadas(){
        return tareaService.listarCompletadas();
    }

    @PostMapping
    public Tarea crear(@RequestBody Tarea tarea){
        return tareaService.guardarTarea(tarea);
    }

}

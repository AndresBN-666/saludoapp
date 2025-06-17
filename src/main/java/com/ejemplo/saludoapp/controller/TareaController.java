package com.ejemplo.saludoapp.controller;

import com.ejemplo.saludoapp.DTO.tarea.TareaActualizarDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaCreateDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaDTO;
import com.ejemplo.saludoapp.model.Tarea;
import com.ejemplo.saludoapp.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TareaDTO> crear(@RequestBody TareaCreateDTO tarea){
        return new ResponseEntity<>(tareaService.crearTarea(tarea), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizar(@PathVariable Long id,
                                               @RequestBody @Valid TareaActualizarDTO tarea){
        return ResponseEntity.ok(tareaService.actualizarTarea(id,tarea));
    }


}

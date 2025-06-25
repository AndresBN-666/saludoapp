package com.ejemplo.saludoapp.controller;

import com.ejemplo.saludoapp.DTO.tarea.TareaActualizarDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaCreateDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaDTO;
import com.ejemplo.saludoapp.model.Tarea;
import com.ejemplo.saludoapp.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarea")
public class TareaController {
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Tarea> obtenerTareas() {
        return tareaService.listarTodas();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/completadas")
    public List<Tarea> tareasCompletadas(){
        return tareaService.listarCompletadas();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TareaDTO> crear(@RequestBody TareaCreateDTO tarea){
        return new ResponseEntity<>(tareaService.crearTarea(tarea), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizar(@PathVariable Long id,
                                               @RequestBody @Valid TareaActualizarDTO tarea){
        return ResponseEntity.ok(tareaService.actualizarTarea(id,tarea));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id){
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/especificacion")
    public ResponseEntity<Page<TareaDTO>> listarTareas(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Boolean completada,
            @RequestParam(required = false) Long usuarioId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ){
        return ResponseEntity.ok(tareaService.listarTeares(titulo,completada,usuarioId,pageable));
    }


}

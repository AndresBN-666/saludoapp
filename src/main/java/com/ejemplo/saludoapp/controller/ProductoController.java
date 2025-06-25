package com.ejemplo.saludoapp.controller;

import com.ejemplo.saludoapp.model.Producto;
import com.ejemplo.saludoapp.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/solo-usuarios")
    public ResponseEntity<String> soloUsuarios(){
        return ResponseEntity.ok("Hola solo usuarios!");
    }

    @GetMapping
    public List<Producto> listarTodos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Producto obtenerPorId(@PathVariable Long id){
        return productoService.obtnerPorId(id);
    }

}

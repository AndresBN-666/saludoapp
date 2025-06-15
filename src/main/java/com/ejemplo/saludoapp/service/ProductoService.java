package com.ejemplo.saludoapp.service;

import com.ejemplo.saludoapp.model.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> obtenerTodos();
    Producto obtnerPorId(Long id);
}

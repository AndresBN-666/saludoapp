package com.ejemplo.saludoapp.serviceImpl;

import com.ejemplo.saludoapp.model.Producto;
import com.ejemplo.saludoapp.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosServiceImpl implements ProductoService {

    private final List<Producto> productos = List.of(
            new Producto(1L, "Teclado", 59.99),
            new Producto(2L, "Mouse", 29.99),
            new Producto(3L, "Pantalla", 199.99)
    );

    @Override
    public List<Producto> obtenerTodos() {
        return productos;
    }

    @Override
    public Producto obtnerPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

    }
}

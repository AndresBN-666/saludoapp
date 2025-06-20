package com.ejemplo.saludoapp.repository;

import com.ejemplo.saludoapp.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol save(String nombre);
}

package com.ejemplo.saludoapp.repository;

import com.ejemplo.saludoapp.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByCompletadaTrue();
}

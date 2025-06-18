package com.ejemplo.saludoapp.repository;

import com.ejemplo.saludoapp.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long>, JpaSpecificationExecutor<Tarea> {
    List<Tarea> findByCompletadaTrue();
}

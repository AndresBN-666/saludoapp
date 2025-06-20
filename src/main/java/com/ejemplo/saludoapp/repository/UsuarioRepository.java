package com.ejemplo.saludoapp.repository;

import com.ejemplo.saludoapp.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    List<Usuario> findByActivoTrue();
    Page<Usuario> findAll(Pageable pageable);
    Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    Optional<Usuario> findByEmail(String email);

}

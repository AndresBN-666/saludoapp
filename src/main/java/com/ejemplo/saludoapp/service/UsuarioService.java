package com.ejemplo.saludoapp.service;

import com.ejemplo.saludoapp.DTO.UsuarioActualizarDTO;
import com.ejemplo.saludoapp.DTO.UsuarioCreateDTO;
import com.ejemplo.saludoapp.DTO.UsuarioDTO;
import com.ejemplo.saludoapp.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    Page<UsuarioDTO> listarTodos(Pageable pageable);
    List<UsuarioDTO> listarActivos();
    UsuarioDTO crear(UsuarioCreateDTO usuarioCreateDTO);
    void eliminar(Long id);
    UsuarioDTO actualizar(Long id, UsuarioActualizarDTO usuarioActualizarDTO);
    UsuarioDTO buscarPorId(Long id);
    Page<UsuarioDTO> buscarPorNombre(String nombre, String email, Boolean activo, Pageable pageable);
    List<UsuarioDTO> listarTodosUsuarios();

}

package com.ejemplo.saludoapp;

import com.ejemplo.saludoapp.DTO.UsuarioCreateDTO;
import com.ejemplo.saludoapp.DTO.UsuarioDTO;
import com.ejemplo.saludoapp.exception.UsuarioNoEncontradoException;
import com.ejemplo.saludoapp.mapper.UsuarioMapper;
import com.ejemplo.saludoapp.model.Usuario;
import com.ejemplo.saludoapp.repository.UsuarioRepository;
import com.ejemplo.saludoapp.serviceImpl.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void testObtenerUsuarioPorId_existente(){
        //Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Andres");
        usuario.setEmail("andres@gmail.com");

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("Andres");
        usuarioDTO.setEmail("andres@gmail.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        //Act
        UsuarioDTO dto = usuarioService.buscarPorId(1L);

        //Assert
        assertEquals(usuarioDTO.getId(), dto.getId());
        assertEquals(usuarioDTO.getNombre(), dto.getNombre());
        assertEquals(usuarioDTO.getEmail(), dto.getEmail());

    }

    @Test
    void testObtenerUsuarioPorId_noExistente_deberiaLanzarExcepcion(){
        //Arrange
        Long idBuscado = 100L;
        when(usuarioRepository.findById(idBuscado)).thenReturn(Optional.empty());

        //Act & Assert
        UsuarioNoEncontradoException exception = assertThrows(UsuarioNoEncontradoException.class,
                () -> usuarioService.buscarPorId(idBuscado));

        assertEquals("No se encontro el usuario con id: " + idBuscado, exception.getMessage());
    }


    @Test
    void testCrearUsuario(){
        //Arrange
        UsuarioCreateDTO dtoEntrada = new UsuarioCreateDTO();
        dtoEntrada.setNombre("Andres");
        dtoEntrada.setEmail("andres@gmail.com");
        dtoEntrada.setClave("123456");

        Usuario entidadParaGuardar = new Usuario();
        entidadParaGuardar.setNombre("Andres");
        entidadParaGuardar.setEmail("andres@gmail.com");
        entidadParaGuardar.setClave("123456");

        Usuario entidadGuardada = new Usuario();
        entidadGuardada.setId(1L);
        entidadGuardada.setNombre("Andres");
        entidadGuardada.setEmail("andres@gmail.com");
        entidadGuardada.setClave("123456");

        UsuarioDTO dtoEsperado= new UsuarioDTO();
        dtoEsperado.setId(1L);
        dtoEsperado.setNombre("Andres");
        dtoEsperado.setEmail("andres@gmail.com");
        dtoEsperado.setClave("123456");

        //simular el comportamiento del mapper y repositorio
        when(usuarioMapper.toEntity(dtoEntrada)).thenReturn(entidadParaGuardar);
        when(usuarioRepository.save(entidadParaGuardar)).thenReturn(entidadGuardada);
        when(usuarioMapper.toDTO(entidadGuardada)).thenReturn(dtoEsperado);

        // Act
        UsuarioDTO resultado = usuarioService.crear(dtoEntrada);

        //Asert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Andres", resultado.getNombre());
        assertEquals("andres@gmail.com", resultado.getEmail());

    }

}

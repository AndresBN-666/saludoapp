package com.ejemplo.saludoapp.Tareas;

import com.ejemplo.saludoapp.DTO.tarea.TareaCreateDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaDTO;
import com.ejemplo.saludoapp.exception.UsuarioNoEncontradoException;
import com.ejemplo.saludoapp.mapper.TareaMapper;
import com.ejemplo.saludoapp.model.Tarea;
import com.ejemplo.saludoapp.model.Usuario;
import com.ejemplo.saludoapp.repository.TareaRepository;
import com.ejemplo.saludoapp.repository.UsuarioRepository;
import com.ejemplo.saludoapp.serviceImpl.TareaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TareaMapper tareaMapper;

    @InjectMocks
    private TareaServiceImpl tareaService;

    @Test
    void crearTarea_DatosValidos_RetornaDTOCreado(){
        //Arrange
        Long usuarioId = 1L;
        TareaCreateDTO tareaCrear = new TareaCreateDTO("titulo", "descripcion", false, usuarioId);
        Usuario usuario = new Usuario(usuarioId, "Nombre", "correo@mail.com", "clave", true);

        Tarea tareaEntity = new Tarea(null, "titulo", "descripcion", false, usuario);
        Tarea tareaGuardada = new Tarea(10L, "titulo", "descripcion", false, usuario);
        TareaDTO tareaToDTO = new TareaDTO(10L, "titulo", "descripcion", false, usuario.getNombre());

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(tareaMapper.toEntity(tareaCrear)).thenReturn(tareaEntity);
        when(tareaRepository.save(tareaEntity)).thenReturn(tareaGuardada);
        when(tareaMapper.toDTO(tareaGuardada)).thenReturn(tareaToDTO);

        //Act
        TareaDTO resultado = tareaService.crearTarea(tareaCrear);

        //Assert

        assertEquals(tareaToDTO.getTitulo(), resultado.getTitulo());
        assertEquals(tareaToDTO.getDescripcion(), resultado.getDescripcion());
        assertEquals(tareaToDTO.getNombreUsuario(), resultado.getNombreUsuario());

    }

    @Test
    void crearTarea_usuarioNoExistente_lanzaError(){
        //Arrange
        Long usuarioId = 99L;
        TareaCreateDTO tareaCrear = new TareaCreateDTO("titulo", "descripcion", false, usuarioId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        //Act & Assert

        //assertThrows(UsuarioNoEncontradoException.class, () -> tareaService.crearTarea(tareaCrear));

        UsuarioNoEncontradoException ex = assertThrows(UsuarioNoEncontradoException.class,
                () -> tareaService.crearTarea(tareaCrear));


        assertEquals("No se encontro el usuario con id: " + usuarioId, ex.getMessage());
        verify(tareaRepository, never()).save(any());

    }


}

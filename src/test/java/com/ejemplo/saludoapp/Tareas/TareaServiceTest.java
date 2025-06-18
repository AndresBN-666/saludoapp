package com.ejemplo.saludoapp.Tareas;

import com.ejemplo.saludoapp.DTO.tarea.TareaActualizarDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaCreateDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaDTO;
import com.ejemplo.saludoapp.exception.UsuarioNoEncontradoException;
import com.ejemplo.saludoapp.exception.tarea.TareaNoEncontradaException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void actualizarTarea_DatosValidos_RetornaDTO(){
        //Arrange
        Long tareaId = 1L;
        TareaActualizarDTO dtoActualizar = new TareaActualizarDTO(
                "Tarea actualizada", "Nueva descripcion", true);

        Usuario usuario = new Usuario(1L, "Nombre", "correo@mail.com", "clave", true);


        Tarea tareaExistenteEnDB  = new Tarea(1L, "titulo", "descripcion", false, usuario);
        Tarea tareaModificadaPorMapper  = new Tarea(1L,"Tarea actualizada", "Nueva descripcion", true, usuario);
        TareaDTO dtoEsperado = new TareaDTO(1L,"Tarea actualizada", "Nueva descripcion", true, usuario.getNombre());

        when(tareaRepository.findById(tareaId)).thenReturn(Optional.of(tareaExistenteEnDB ));
        doAnswer(invocation ->{
            TareaActualizarDTO sourceDto = invocation.getArgument(0);
            Tarea targetEntity = invocation.getArgument(1);

            targetEntity.setTitulo(sourceDto.getTitulo());
            targetEntity.setDescripcion(sourceDto.getDescripcion());
            targetEntity.setCompletada(sourceDto.isCompletada());
            return null;
        }).when(tareaMapper).actualizarEntidadDesdeDto(any(TareaActualizarDTO.class), eq(tareaExistenteEnDB));

        when(tareaRepository.save(any(Tarea.class))).thenReturn(tareaModificadaPorMapper);
        when(tareaMapper.toDTO(tareaModificadaPorMapper)).thenReturn(dtoEsperado);

        //Act
        TareaDTO resultado = tareaService.actualizarTarea(tareaId, dtoActualizar);
        // Assert
        assertEquals(dtoEsperado.getId(), resultado.getId());
        assertEquals(dtoEsperado.getTitulo(), resultado.getTitulo());
        assertEquals(dtoEsperado.getDescripcion(), resultado.getDescripcion());
        assertEquals(dtoEsperado.isCompletada(), resultado.isCompletada());
        assertEquals(dtoEsperado.getNombreUsuario(), resultado.getNombreUsuario()); // Si tu DTO tiene esto

        // Verificaciones de interacciones
        verify(tareaRepository, times(1)).findById(tareaId);
        // Verifica que actualizarEntidadDesdeDto fue llamado
        verify(tareaMapper, times(1)).actualizarEntidadDesdeDto(dtoActualizar, tareaExistenteEnDB);
        // Verifica que save fue llamado con una Tarea
        verify(tareaRepository, times(1)).save(any(Tarea.class)); // Opcional: si quieres ser muy estricto, puedes capturar el argumento para verificar su estado
        verify(tareaMapper, times(1)).toDTO(tareaModificadaPorMapper);


    }

    @Test
    void actualizarTarea_IdInvalido_RetornaError(){
        Long tareaId = 99L;

        TareaActualizarDTO dtoActualizar = new TareaActualizarDTO("titulo", "descripcion", true);

        when(tareaRepository.findById(tareaId)).thenReturn(Optional.empty());

        TareaNoEncontradaException ex = assertThrows(TareaNoEncontradaException.class,
                ()-> tareaService.actualizarTarea(tareaId, dtoActualizar));

        assertEquals("No se encontro la tarea con id: "+ tareaId, ex.getMessage());
    }

    @Test
    void eliminarTarea_IdValido(){
        Long tareaId = 1L;

        Tarea tarea = new Tarea();
        tarea.setId(tareaId);

        when(tareaRepository.findById(tareaId)).thenReturn(Optional.of(tarea));

        assertDoesNotThrow(() -> tareaService.eliminarTarea(tareaId));

        verify(tareaRepository, times(1)).findById(tareaId);
        verify(tareaRepository, times(1)).delete(tarea);




    }

    @Test
    void eliminarTarea_IdInvalido_RetornaError(){
        Long tareaId = 99L;
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        when(tareaRepository.findById(tareaId)).thenReturn(Optional.empty());

        TareaNoEncontradaException ex = assertThrows(TareaNoEncontradaException.class,
                () -> tareaService.eliminarTarea(tareaId));

        verify(tareaRepository, never()).delete((Tarea) any());
        assertEquals("No se encontro la tarea con id: "+ tareaId, ex.getMessage());
    }

    @Test
    void listarTareas_ConFiltrosYPageable_RetornaPageDTO(){
        //Arrange
        String titulo = "Importante";
        Boolean completada = true;
        Long usuarioId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Usuario usuario = new Usuario(1L, "Nombre", "correo@mail.com", "clave", true);
        Tarea tarea = new Tarea(1L, "titulo", "desc", true, usuario);
        TareaDTO dto = new TareaDTO(1L, "Importante", "desc", true, usuario.getNombre());

        Page<Tarea> tareasPage = new PageImpl<>(List.of(tarea));
        Page<TareaDTO> tareasPageDto = new PageImpl<>(List.of(dto));

        when(tareaRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(tareasPage);
        when(tareaMapper.toDTOPage(tareasPage)).thenReturn(tareasPageDto);

        //Act
        Page<TareaDTO> resultado = tareaService.listarTeares(titulo,completada,usuarioId,pageable);

        //Asert

        assertEquals(1, resultado.getContent().size());
        assertEquals("Importante", resultado.getContent().get(0).getTitulo());

        verify(tareaRepository).findAll(any(Specification.class), eq(pageable));
        verify(tareaMapper).toDTOPage(tareasPage);

    }


}

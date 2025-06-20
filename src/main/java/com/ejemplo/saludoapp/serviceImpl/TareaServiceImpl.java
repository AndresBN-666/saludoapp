package com.ejemplo.saludoapp.serviceImpl;

import com.ejemplo.saludoapp.DTO.tarea.TareaActualizarDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaCreateDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaDTO;
import com.ejemplo.saludoapp.especificaciones.TareaSpecifications;
import com.ejemplo.saludoapp.exception.UsuarioNoEncontradoException;
import com.ejemplo.saludoapp.exception.tarea.TareaNoEncontradaException;
import com.ejemplo.saludoapp.mapper.TareaMapper;
import com.ejemplo.saludoapp.model.Tarea;
import com.ejemplo.saludoapp.model.Usuario;
import com.ejemplo.saludoapp.repository.TareaRepository;
import com.ejemplo.saludoapp.repository.UsuarioRepository;
import com.ejemplo.saludoapp.service.TareaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl implements TareaService {

/*    private final List<Tarea> tareas = List.of(
            new Tarea(1L, "Estudiar Spring", false),
            new Tarea(2L, "Leer un libro", true),
            new Tarea(3L, "Hacer ejercicio", false)
    );*/

    private final TareaMapper tareaMapper;

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    public TareaServiceImpl(TareaMapper tareaMapper, TareaRepository tareaRepository, UsuarioRepository usuarioRepository) {
        this.tareaMapper = tareaMapper;
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public List<Tarea> listarTodas() {
        return tareaRepository.findAll();
    }

    @Override
    public List<Tarea> listarCompletadas() {
        return tareaRepository.findByCompletadaTrue();
    }

    @Override
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public TareaDTO crearTarea(TareaCreateDTO tarea) {
        Usuario usuario = usuarioRepository.findById(tarea.getUsuarioId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(tarea.getUsuarioId()));

        Tarea tareaGuardar = tareaMapper.toEntity(tarea);
        tareaGuardar.setUsuario(usuario);

        return tareaMapper.toDTO(tareaRepository.save(tareaGuardar));
    }

    @Override
    public TareaDTO actualizarTarea(Long id,TareaActualizarDTO tarea) {
        Tarea tareaExistente = tareaRepository.findById(id)
                .orElseThrow(() -> new TareaNoEncontradaException(id));

        //Obtener Usuario Autenticado
        String emailAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioAutenticado = usuarioRepository.findByEmail(emailAutenticado)
                        .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        // Validar si es el dueño o tiene rol Admin
        boolean esAdmin = usuarioAutenticado.getRoles().stream()
                        .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("ADMIN"));

        if (!tareaExistente.getUsuario().getId().equals(usuarioAutenticado.getId()) && !esAdmin) {
            throw new RuntimeException("No tienes permisos para actualizar esta tarea.");
        }


        tareaMapper.actualizarEntidadDesdeDto(tarea, tareaExistente);
        tareaExistente = tareaRepository.save(tareaExistente);

        return tareaMapper.toDTO(tareaExistente);
    }

    @Override
    public void eliminarTarea(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(()-> new TareaNoEncontradaException(id));
        tareaRepository.delete(tarea);
    }

    @Override
    public Page<TareaDTO> listarTeares(String titulo, Boolean completada, Long usuarioId, Pageable pageable) {
        Specification<Tarea> spec = Specification.where(TareaSpecifications.tituloContiene(titulo))
                .and(TareaSpecifications.completadaContiene(completada))
                .and(TareaSpecifications.usuarioContiene(usuarioId));

        Page<Tarea> tarea = tareaRepository.findAll(spec, pageable);
        //return tarea.map(tareaMapper :: toDTO);
        return tareaMapper.toDTOPage(tarea);
    }
}

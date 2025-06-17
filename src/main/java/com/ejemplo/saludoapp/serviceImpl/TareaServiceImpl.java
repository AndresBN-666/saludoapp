package com.ejemplo.saludoapp.serviceImpl;

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
import com.ejemplo.saludoapp.service.TareaService;
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

        tareaMapper.actualizarEntidadDesdeDto(tarea, tareaExistente);
        tareaExistente = tareaRepository.save(tareaExistente);

        return tareaMapper.toDTO(tareaExistente);
    }
}

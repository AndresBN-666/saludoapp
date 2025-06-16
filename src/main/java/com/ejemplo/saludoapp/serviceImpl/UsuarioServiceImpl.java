package com.ejemplo.saludoapp.serviceImpl;

import com.ejemplo.saludoapp.DTO.UsuarioActualizarDTO;
import com.ejemplo.saludoapp.DTO.UsuarioCreateDTO;
import com.ejemplo.saludoapp.DTO.UsuarioDTO;
import com.ejemplo.saludoapp.especificaciones.UsuarioSpecifications;
import com.ejemplo.saludoapp.exception.UsuarioNoEncontradoException;
import com.ejemplo.saludoapp.mapper.UsuarioMapper;
import com.ejemplo.saludoapp.model.Usuario;
import com.ejemplo.saludoapp.repository.UsuarioRepository;
import com.ejemplo.saludoapp.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    private Usuario convertiToEntity(UsuarioCreateDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setClave(usuarioDTO.getClave());
        usuario.setActivo(true);
        return usuario;
    }


    private UsuarioDTO convertirToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setClave(usuario.getClave());
        return usuarioDTO;
    }


    @Override
    public Page<UsuarioDTO> listarTodos(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarioMapper.toDTOPage(usuarios);
    }

    @Override
    public List<UsuarioDTO> listarActivos() {
        return usuarioMapper.toDTOList(usuarioRepository.findByActivoTrue());
    }

    @Override
    public UsuarioDTO crear(UsuarioCreateDTO  usuarioCreateDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioCreateDTO);
        usuario.setActivo(true);
        Usuario usuarioGuardar = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioGuardar);
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        usuarioRepository.delete(usuario);
    }

    @Override
    public UsuarioDTO actualizar(Long id, UsuarioActualizarDTO usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        usuarioMapper.actualizarDesdeDTO(usuario, usuarioExistente);

        Usuario actualizado = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toDTO(actualizado);

    }

    @Override
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public Page<UsuarioDTO> buscarPorNombre(String nombre, String email, Boolean activo, Pageable pageable) {
        Specification<Usuario> spec = Specification.where(UsuarioSpecifications.nombreContiene(nombre))
                .and(UsuarioSpecifications.emailContiene(email))
                .and(UsuarioSpecifications.esActivo(activo));
        Page<Usuario> usuarios = usuarioRepository.findAll(spec, pageable);
        return usuarioMapper.toDTOPage(usuarios);
    }

    @Override
    public List<UsuarioDTO> listarTodosUsuarios() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }


}

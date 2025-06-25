package com.ejemplo.saludoapp.controller;

import com.ejemplo.saludoapp.DTO.UsuarioActualizarDTO;
import com.ejemplo.saludoapp.DTO.UsuarioCreateDTO;
import com.ejemplo.saludoapp.DTO.UsuarioDTO;
import com.ejemplo.saludoapp.model.Usuario;
import com.ejemplo.saludoapp.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<UsuarioDTO>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(usuarioService.listarTodos(pageable));
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<UsuarioDTO>> buscarPorFiltro(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Sort sort = direction.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(usuarioService.buscarPorNombre(nombre, email, activo, pageable));
/*        if (nombre != null && !nombre.isEmpty()) {
            return ResponseEntity.ok(usuarioService.buscarPorNombre(nombre, pageable));
        }else{
            return ResponseEntity.ok(usuarioService.listarTodos(pageable));
        }*/
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/activos")
    public List<UsuarioDTO> listarActivos(){
        return usuarioService.listarActivos();
    }

    @GetMapping("/perfil")
    public ResponseEntity<Map<String, Object>> obtenerPerfilActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> response = new HashMap<>();
        response.put("usuario", auth.getName());

        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        response.put("roles", roles);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody @Valid UsuarioCreateDTO dto) {
        UsuarioDTO creado = usuarioService.crear(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editarUsuario(@RequestBody @Valid UsuarioActualizarDTO usuario,
                                 @PathVariable Long id) {
        UsuarioDTO usuarioDTO = usuarioService.actualizar(id, usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioDTO>> listarTodosLosUsuario(){
        List<UsuarioDTO> usuarioDTO = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarioDTO);
    }
}

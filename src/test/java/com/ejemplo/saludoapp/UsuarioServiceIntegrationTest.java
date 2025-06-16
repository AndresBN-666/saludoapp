package com.ejemplo.saludoapp;

import com.ejemplo.saludoapp.DTO.UsuarioActualizarDTO;
import com.ejemplo.saludoapp.DTO.UsuarioCreateDTO;
import com.ejemplo.saludoapp.DTO.UsuarioDTO;
import com.ejemplo.saludoapp.mapper.UsuarioMapper;
import com.ejemplo.saludoapp.model.Usuario;
import com.ejemplo.saludoapp.repository.UsuarioRepository;
import com.ejemplo.saludoapp.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioServiceIntegrationTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll(); // Limpia la base para evitar conflictos
    }

    @Test
    void crearUsuario_DeberiaGuardarYRetornarDTO(){
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("Andres", "andres@email.com", "claves");

        UsuarioDTO resultado = usuarioService.crear(usuarioCreateDTO);

        assertNotNull(resultado.getId());
        assertEquals("Andres", resultado.getNombre());
        assertEquals("andres@email.com", resultado.getEmail());
        assertTrue(resultado.isActivo());
    }

    @Test
    void obtenerUsuario_IdValido_DeberiaRetornarUsuarioDTO(){

        Usuario usuario = new Usuario(null, "andres", "andres@emeail.com", "123456", true);
        usuario = usuarioRepository.save(usuario);

        UsuarioDTO usuarioDTO = usuarioService.buscarPorId(usuario.getId());
        assertNotNull(usuarioDTO.getId());
        assertEquals("andres", usuarioDTO.getNombre());
    }

    @Test
    void usuarioActualizar_DeberiaActualizarCampos(){
        Usuario usuario = new Usuario(null, "andres", "andres@email.com", "123456", false);
        usuario = usuarioRepository.save(usuario);

        UsuarioActualizarDTO usuarioActualizarDTO = new UsuarioActualizarDTO("andres barcena", "aaa@email.com", true);
        UsuarioDTO usuarioActualizado = usuarioService.actualizar(usuario.getId(), usuarioActualizarDTO);

        assertEquals("andres barcena", usuarioActualizado.getNombre());
        assertTrue(usuarioActualizado.isActivo());

    }
}

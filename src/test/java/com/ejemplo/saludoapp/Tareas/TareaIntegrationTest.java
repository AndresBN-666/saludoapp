package com.ejemplo.saludoapp.Tareas;

import com.ejemplo.saludoapp.model.Usuario;
import com.ejemplo.saludoapp.repository.TareaRepository;
import com.ejemplo.saludoapp.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TareaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @BeforeEach
    void setUp(){
        tareaRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    void crearTarea_deberiaGuardarYResponderConDTO() throws Exception {
        Usuario usuario = new Usuario(null, "Juan", "juan@email.com", "clave", true);
        usuario = usuarioRepository.save(usuario);

        String bodyJson = """
                {
                    "titulo": "Tarea 1",
                    "descripcion": "Descripción",
                    "completada": false,
                    "usuarioId": %d
                }
                """.formatted(usuario.getId());

        mockMvc.perform(post("/tarea")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Tarea 1"))
                .andExpect(jsonPath("$.nombreUsuario").value("Juan"));

    }

    @Test
    void crearTarea_usuarioNoExistente_deberiaRetornar404() throws Exception {

        Long usuarioIdInexistente = 999L;

        String bodyJson = """
                {
                    "titulo": "Tarea fallida",
                    "descripcion": "No se debería guardar",
                    "completada": false,
                    "usuarioId": %d
                }
                """.formatted(usuarioIdInexistente);

        mockMvc.perform(post("/tarea")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No se encontro el usuario con id: "
                        + usuarioIdInexistente));
    }

}

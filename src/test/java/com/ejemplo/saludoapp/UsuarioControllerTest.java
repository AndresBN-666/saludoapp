package com.ejemplo.saludoapp;

import com.ejemplo.saludoapp.DTO.UsuarioActualizarDTO;
import com.ejemplo.saludoapp.DTO.UsuarioCreateDTO;
import com.ejemplo.saludoapp.DTO.UsuarioDTO;
import com.ejemplo.saludoapp.controller.UsuarioController;
import com.ejemplo.saludoapp.exception.UsuarioNoEncontradoException;
import com.ejemplo.saludoapp.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void eliminarUsuario_deberiaRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/usuario/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarUsario_UsuarioNoEncontrado() throws Exception {
        doThrow(new UsuarioNoEncontradoException(1L))
                .when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/usuario/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No se encontro el usuario con id: " + 1L));
    }

    @Test
    void actualizarUsuario_deberiaRetornarUsuarioActualizado() throws Exception {
        //Arrange
        Long id = 1L;
        UsuarioActualizarDTO usuarioActualizarDTO = new UsuarioActualizarDTO("andres", "andres@email.com",
                true);
        UsuarioDTO usuarioActualizado = new UsuarioDTO(1L, "andres", "andres@email.com",
                true);

        when(usuarioService.actualizar(eq(1L), any(UsuarioActualizarDTO.class))).thenReturn(usuarioActualizado);
        String jsonRequest = """
                {
                    "nombre": "andres",
                    "email": "andres@email.com",
                    "activo" : true
                }
                """;
        mockMvc.perform(put("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value(usuarioActualizarDTO.getNombre()))
                .andExpect(jsonPath("$.email").value(usuarioActualizarDTO.getEmail()))
                .andExpect(jsonPath("$.activo").value(usuarioActualizarDTO.isActivo()));

    }

    @Test
    void actualizarUsuario_conDatosInvalidos_deberiaRetornarErroresDeValidacion() throws Exception {
        String invalido = """
                {
                    "nombre": "",
                    "email": "correo-no-valido",
                    "activo" : false
                }
                """;

        mockMvc.perform(put("/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombre").value("EL nombre no puede estar vacio"))
                .andExpect(jsonPath("$.email").value("Direccion de correo invalido"));
    }

    @Test
    void obtenerPorId_deberiaRetornarUsuario() throws Exception {
        //Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "andres", "andres@email.com", "123456");
        when(usuarioService.buscarPorId(1L)).thenReturn(usuarioDTO);

        //Act & Assert
        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("andres"))
                .andExpect(jsonPath("$.email").value("andres@email.com"))
                .andExpect(jsonPath("$.clave").value("123456"));
    }

    @Test
    void crearUsuario_deberiaRetornarUsuarioCreado() throws Exception {
        //Arrange
        UsuarioDTO nuevoUsuario = new UsuarioDTO(null, "andres", "andres@email.com", "123456");
        UsuarioCreateDTO usuarioCreado = new UsuarioCreateDTO("andres", "andres@email.com", "123456");

        when(usuarioService.crear(any(UsuarioCreateDTO.class))).thenReturn(nuevoUsuario);
        //Act & Assert

        mockMvc.perform(post("/usuario")
                        .contentType("application/json")
                        .content("""
                                    {
                                        "nombre": "andres",
                                        "email": "andres@email.com",
                                        "clave": "123456"
                                    }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("andres"))
                .andExpect(jsonPath("$.email").value("andres@email.com"))
                .andExpect(jsonPath("$.clave").value("123456"));
    }

    @Test
    void crearUsuario_conDatosInvalidos_deberiaRetornarErroresDeValidacion() throws Exception {
        String invalido = """
                {
                    "nombre": "",
                    "email": "no-es-un-email",
                    "clave": "123"
                }
                """;

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombre").value("No puede estar en blanco"))
                .andExpect(jsonPath("$.email").value("Correo no valido"))
                .andExpect(jsonPath("$.clave").value("La contraseña debe tener como minimo 6 caracteres"));

    }
}

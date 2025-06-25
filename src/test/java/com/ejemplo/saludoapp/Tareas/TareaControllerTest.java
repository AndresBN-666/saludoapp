package com.ejemplo.saludoapp.Tareas;

import com.ejemplo.saludoapp.controller.TareaController;
import com.ejemplo.saludoapp.seguridad.jwt.JwtAuthenticationFilter;
import com.ejemplo.saludoapp.service.TareaService;
import com.ejemplo.saludoapp.serviceImpl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TareaController.class)
public class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TareaService tareaService;


    // Simula un usuario autenticado con el rol USER
    @Test
    @WithMockUser(username = "andres", roles = {"USER"})
    void tareasCompletadas_conRolUser_retorna200() throws Exception {
        when(tareaService.listarCompletadas()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/completadas"))
                .andExpect(status().isOk());
    }

    // Simula un usuario sin rol USER
    @Test
    @WithMockUser(username = "andres", roles = {"ADMIN"})
    void tareasCompletadas_conRolAdmin_forbidden() throws Exception {
        mockMvc.perform(get("/completadas"))
                .andExpect(status().isForbidden());
    }

}

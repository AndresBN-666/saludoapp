package com.ejemplo.saludoapp.Tareas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TareaIntegrationTest1 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void tareasCompletadas_conTokenValido_retorna200() throws Exception {
        // login con credenciales validas
        String email = "andres@example.com";
        String clave = "123456";
        String loginJson = mapper.writeValueAsString(
                Map.of("email", email, "clave", clave)
        );

        MvcResult result = mockMvc.perform(post("auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        // 2. Extraer token
        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = mapper.readTree(responseBody);
        String token = jsonNode.get("token").asText();

        // 3. Llamar al endpoint protegido
        mockMvc.perform(get("/completadas")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());

    }
}

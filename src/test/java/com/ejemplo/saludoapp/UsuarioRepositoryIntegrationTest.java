package com.ejemplo.saludoapp;

import com.ejemplo.saludoapp.model.Usuario;
import com.ejemplo.saludoapp.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(locations = "classpath:application-test.properties")
public class UsuarioRepositoryIntegrationTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    void testGuardarYBuscarUsuario(){
        //Arrange
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario");
        usuario.setEmail("usuario@email.com");

        //Act
        Usuario usuarioGuardado = repository.save(usuario);

        //Assert
        Optional<Usuario> encontrado = repository.findById(usuarioGuardado.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Usuario", encontrado.get().getNombre());

    }
}

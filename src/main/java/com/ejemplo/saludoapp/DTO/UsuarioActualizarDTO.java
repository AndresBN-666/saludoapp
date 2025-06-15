package com.ejemplo.saludoapp.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioActualizarDTO {

    @NotBlank(message = "EL nombre no puede estar vacio")
    private String nombre;

    @Email(message = "Direccion de correo invalido")
    @NotBlank(message = "Correo no puede estar vacio")
    private String email;

    private boolean activo;

    public UsuarioActualizarDTO() {
    }

    public UsuarioActualizarDTO(String nombre, String email, boolean activo) {
        this.nombre = nombre;
        this.email = email;
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public @NotBlank(message = "EL nombre no puede estar vacio") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "EL nombre no puede estar vacio") String nombre) {
        this.nombre = nombre;
    }

    public @Email(message = "Direccion de correo invalido") @NotBlank(message = "Correo no puede estar vacio") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Direccion de correo invalido") @NotBlank(message = "Correo no puede estar vacio") String email) {
        this.email = email;
    }
}

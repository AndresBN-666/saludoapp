package com.ejemplo.saludoapp.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UsuarioCreateDTO {

    @NotBlank(message = "No puede estar en blanco")
    private String nombre;

    @Email(message = "Correo no valido")
    @NotBlank(message = "Campo correo no puede estar vacio")
    private String email;

    @NotBlank(message = "Clave no puede estar vacio")
    @Size(min = 6, message = "La contraseña debe tener como minimo 6 caracteres")
    private String clave;

    private Set<Long> rolesIds;

    public UsuarioCreateDTO() {
    }

    public UsuarioCreateDTO(String nombre, String email, String clave) {
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
    }

    public @NotBlank(message = "No puede estar en blanco") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "No puede estar en blanco") String nombre) {
        this.nombre = nombre;
    }

    public @Email(message = "Correo no valido") @NotBlank(message = "Campo correo no puede estar vacio") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Correo no valido") @NotBlank(message = "Campo correo no puede estar vacio") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Clave no puede estar vacio") @Size(min = 6, message = "La contraseña debe tener como minimo 6 caracteres") String getClave() {
        return clave;
    }

    public void setClave(@NotBlank(message = "Clave no puede estar vacio") @Size(min = 6, message = "La contraseña debe tener como minimo 6 caracteres") String clave) {
        this.clave = clave;
    }

    public Set<Long> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(Set<Long> rolesIds) {
        this.rolesIds = rolesIds;
    }
}

package com.ejemplo.saludoapp.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    @Email(message = "Correo no valido")
    @NotBlank(message = "No debe estar en blanco")
    private String email;
    private String clave;
    private boolean activo;
    private Set<String> nombreRol;


    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String email, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.activo = activo;
    }

    public UsuarioDTO(Long id, String nombre, String email, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<String> getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(Set<String> nombreRol) {
        this.nombreRol = nombreRol;
    }
}

package com.ejemplo.saludoapp.DTO.tarea;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TareaCreateDTO {

    @NotBlank(message = "Campo no puede estar vacio")
    private String titulo;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    private boolean completada;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long usuarioId;

    public TareaCreateDTO(String titulo, String descripcion, boolean completada, Long usuarioId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
        this.usuarioId = usuarioId;
    }

    public TareaCreateDTO() {
    }

    public @NotBlank(message = "Campo no puede estar vacio") String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NotBlank(message = "Campo no puede estar vacio") String titulo) {
        this.titulo = titulo;
    }

    public @NotBlank(message = "La descripcion es obligatoria") String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NotBlank(message = "La descripcion es obligatoria") String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public @NotNull(message = "El id del usuario es obligatorio") Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(@NotNull(message = "El id del usuario es obligatorio") Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

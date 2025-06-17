package com.ejemplo.saludoapp.DTO.tarea;

import jakarta.validation.constraints.NotBlank;

public class TareaActualizarDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private boolean completada;

    public TareaActualizarDTO() {
    }

    public TareaActualizarDTO(String titulo, String descripcion, boolean completada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
    }

    public @NotBlank(message = "El título es obligatorio") String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NotBlank(message = "El título es obligatorio") String titulo) {
        this.titulo = titulo;
    }

    public @NotBlank(message = "La descripción es obligatoria") String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NotBlank(message = "La descripción es obligatoria") String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}

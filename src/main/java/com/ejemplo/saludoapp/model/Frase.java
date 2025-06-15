package com.ejemplo.saludoapp.model;

public class Frase {
    private Long id;
    private String mensaje;

    public Frase(Long id, String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

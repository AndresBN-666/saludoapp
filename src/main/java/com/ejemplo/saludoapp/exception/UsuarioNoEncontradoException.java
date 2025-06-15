package com.ejemplo.saludoapp.exception;

public class UsuarioNoEncontradoException extends RuntimeException{
    public UsuarioNoEncontradoException(Long id) {
        super("No se encontro el usuario con id: " + id);
    }
}

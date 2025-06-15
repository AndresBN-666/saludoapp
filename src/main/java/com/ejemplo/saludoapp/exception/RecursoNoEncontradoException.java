package com.ejemplo.saludoapp.exception;

public class RecursoNoEncontradoException extends RuntimeException{

    public RecursoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}

package com.ejemplo.saludoapp.exception.tarea;

public class TareaNoEncontradaException extends RuntimeException{
    public TareaNoEncontradaException(Long id){
        super("No se encontro la tarea con id: " + id);
    }
}

package com.ejemplo.saludoapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<String> handleNotFound(RecursoNoEncontradoException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String,String>> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex){
        Map<String,String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }

    // ⚠️ Validaciones con @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> errores = new HashMap<>();

        /*ex.getBindingResult().getAllErrors().forEach(error -> {
                    String campo = ((FieldError) error).getField();
                    String mensaje = error.getDefaultMessage();
                    errores.put(campo, mensaje);*/

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    // ❌ Excepciones generales (como entidad no encontrada)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex){

        Map<String, String> errores = new HashMap<>();
        errores.put("error", ex.getMessage());
        return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex){
        Map<String, String> respuesta= new HashMap<>();
        respuesta.put("error", "Error interno: " + ex.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

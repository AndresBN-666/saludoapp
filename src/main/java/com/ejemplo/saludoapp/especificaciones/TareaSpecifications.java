package com.ejemplo.saludoapp.especificaciones;

import com.ejemplo.saludoapp.model.Tarea;
import org.springframework.data.jpa.domain.Specification;

public class TareaSpecifications {

    public static Specification<Tarea> tituloContiene(String titulo) {
        return (root, query, criteriaBuilder) -> titulo == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")),
                        "%" + titulo.toLowerCase()+ "%");
    }

    public static Specification<Tarea> completadaContiene(Boolean completada) {
        return (root, query, cb) -> completada == null ? null :
                cb.equal(root.get("completada"), completada);
    }

    public static Specification<Tarea> usuarioContiene (Long usuarioId){
        return (root, query, cb) -> usuarioId == null ? null :
                cb.equal(root.get("usuario").get("id"), usuarioId);
    }
}

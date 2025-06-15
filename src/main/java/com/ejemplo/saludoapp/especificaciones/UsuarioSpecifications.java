package com.ejemplo.saludoapp.especificaciones;

import com.ejemplo.saludoapp.model.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecifications {

    public static Specification<Usuario> nombreContiene(String nombre) {
        return (root, query, criteriaBuilder) -> nombre == null ? null : criteriaBuilder
                .like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.toLowerCase()+ "%");
    }

    public static Specification<Usuario> emailContiene(String email) {
        return (root, query, criteriaBuilder) -> email == null ? null : criteriaBuilder
                .like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Usuario> esActivo(Boolean activo){
        return (root, query, criteriaBuilder) -> activo == null ? null : criteriaBuilder
                .equal(root.get("activo"), activo);
    }
}

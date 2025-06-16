package com.ejemplo.saludoapp.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany (mappedBy = "roles")
    private Set<Usuario> usuarios;
}

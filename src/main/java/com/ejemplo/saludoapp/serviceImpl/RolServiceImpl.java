package com.ejemplo.saludoapp.serviceImpl;

import com.ejemplo.saludoapp.model.Rol;
import com.ejemplo.saludoapp.repository.RolRepository;
import com.ejemplo.saludoapp.service.RolService;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }
}

package com.ejemplo.saludoapp.mapper;

import com.ejemplo.saludoapp.DTO.UsuarioActualizarDTO;
import com.ejemplo.saludoapp.DTO.UsuarioCreateDTO;
import com.ejemplo.saludoapp.DTO.UsuarioDTO;
import com.ejemplo.saludoapp.model.Rol;
import com.ejemplo.saludoapp.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Usuario toEntity(UsuarioCreateDTO usuarioDTO);

    @Mapping(source = "roles", target = "nombreRol")
    UsuarioDTO toDTO(Usuario usuario);

    Usuario toEntity(UsuarioActualizarDTO usuarioActualizarDTO);
    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);
    default Page<UsuarioDTO> toDTOPage(Page<Usuario> usuarios) {
        return usuarios.map(this::toDTO);
    }


    @Mapping(target = "id", ignore = true)
    void actualizarDesdeDTO(UsuarioActualizarDTO dto,@MappingTarget Usuario usuario);

    default String mapRolToString(Rol rol) {
        return rol != null ? rol.getNombre() : null;
    }

}

package com.ejemplo.saludoapp.mapper;

import com.ejemplo.saludoapp.DTO.tarea.TareaActualizarDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaCreateDTO;
import com.ejemplo.saludoapp.DTO.tarea.TareaDTO;
import com.ejemplo.saludoapp.model.Tarea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TareaMapper {

    @Mapping(source = "usuario.nombre", target = "nombreUsuario")
    TareaDTO toDTO(Tarea tarea);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Tarea toEntity(TareaCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Tarea toEntity(TareaActualizarDTO dto);

    @Mapping(target = "usuario", ignore = true)
    void actualizarEntidadDesdeDto(TareaActualizarDTO dto, @MappingTarget Tarea entidad);

    List<TareaDTO> toDTOList(List<Tarea> tareas);

}

package com.nave.spring356.nave.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nave.spring356.nave.models.dtos.NaveDto;
import com.nave.spring356.nave.models.entity.Nave;
import com.nave.spring356.nave.models.requests.NaveRequest;
import com.nave.spring356.nave.models.responses.NaveResponse;

@Mapper(componentModel = "spring")
public interface NaveMapper {
    
    @Mapping(target = "id",ignore = true)
    Nave requestToEntity(final NaveRequest nave);

    NaveResponse entityToResponse(final Nave nave);

    NaveDto entityToDto(final Nave nave);    
    
}

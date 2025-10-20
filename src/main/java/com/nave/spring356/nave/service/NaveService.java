package com.nave.spring356.nave.service;

import java.util.List;
import java.util.UUID;

import com.nave.spring356.nave.models.dtos.PageDto;
import com.nave.spring356.nave.models.entity.Nave;
import com.nave.spring356.nave.models.requests.NaveRequest;
import com.nave.spring356.nave.models.responses.NaveResponse;

public interface NaveService {
    NaveResponse getNaveResponse(final UUID id);
    NaveResponse createNave(final NaveRequest nave);    
    PageDto<NaveResponse> getAllNaves(final int pageNum, final int pageSize);
    List<Nave> getByNombreContaining(final String nombre);
    void deleteNave (final UUID id);
    void updateNave(final Nave nave);       
    
}

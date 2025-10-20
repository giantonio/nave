package com.nave.spring356.nave.service;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nave.spring356.nave.exceptions.ResourceNotFoundException;
import com.nave.spring356.nave.mapper.NaveMapper;
import com.nave.spring356.nave.models.dtos.PageDto;
import com.nave.spring356.nave.models.entity.Nave;
import com.nave.spring356.nave.models.requests.NaveRequest;
import com.nave.spring356.nave.models.responses.NaveResponse;
import com.nave.spring356.nave.repository.NaveRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NaveServiceImpl implements NaveService{
    
    private final NaveRepository naveRepo;    
    private final NaveMapper naveMapper;

    @Override
    public NaveResponse createNave(final NaveRequest nave){
        final Nave result = naveMapper.requestToEntity(nave);
        final Nave newNave = naveRepo.save(result);
        return naveMapper.entityToResponse(newNave);
    }

    @Cacheable("naves")
    @Override
    public PageDto<NaveResponse> getAllNaves(final int pageNum, final int pageSize){
        final Pageable sortedByName = PageRequest.of(pageNum, pageSize);
        return naveRepo
            .finAllNaves(sortedByName)
            .map(naveMapper::entityToResponse);
    }

    @Override
    public NaveResponse getNaveResponse(final UUID id){
        return naveRepo
                    .findById(id)
                    .map(naveMapper::entityToResponse)
                    .orElseThrow(
                        () -> new ResourceNotFoundException("La Nave con id " + id + " no existe")
                    );        
    }

    @Override
    public List<Nave> getByNombreContaining(final String nombre){
        return naveRepo.findByNombreContainingIgnoreCase(nombre);        
    }

    @Override
    public void deleteNave (final UUID id){
        final Nave nave = naveRepo
                .findById(id)
                .orElseThrow(
                    () -> new ResourceNotFoundException("La Nave con id" + id + " no existe")
                );

        naveRepo.deleteNave(id);
    }

    @Override 
    public void updateNave(final Nave nave){
        Nave existNave = naveRepo
                .findById(nave.getId())
                .orElseThrow();
        existNave.setNombre(nave.getNombre());
        existNave.setModelo(nave.getModelo());
        existNave.setEmpresa(nave.getEmpresa());
        existNave.setSizeTripulacion(nave.getSizeTripulacion());

        naveRepo.save(existNave);        

    }

    
}

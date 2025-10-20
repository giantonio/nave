package com.nave.spring356.nave.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nave.spring356.nave.mapper.PageNaveMapper;
import com.nave.spring356.nave.models.dtos.PageDto;
import com.nave.spring356.nave.models.entity.Nave;
import com.nave.spring356.nave.repository.jpa.NaveJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NaveRepositoryImpl implements NaveRepository{

    private final NaveJpaRepository naveJpaRepo;    
    private final PageNaveMapper<Nave> navePageMapper = new PageNaveMapper<>();    

    @Override
    public Nave save(final Nave nave){
        return naveJpaRepo.save(nave);
    }

    @Override
    public PageDto<Nave> finAllNaves(final Pageable pageable){       
        final Page<Nave> page = naveJpaRepo.findAll(pageable);  
        return navePageMapper.entityToDto(page);
    }

    @Override
    public Optional<Nave> findById(final UUID id){
        return naveJpaRepo.findById(id);
    }

    @Override
    public List<Nave> findByNombreContainingIgnoreCase(final String nombre){
        return  naveJpaRepo.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public void deleteNave (final UUID id){
        naveJpaRepo.deleteById(id);
    }

    @Override
    public void updateNave(final Nave nave){
        naveJpaRepo.save(nave);        
    }

    
}

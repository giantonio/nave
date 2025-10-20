package com.nave.spring356.nave.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.nave.spring356.nave.models.dtos.PageDto;
import com.nave.spring356.nave.models.entity.Nave;

public interface NaveRepository {
    Optional<Nave> findById(final UUID id);
    Nave save(final Nave nave);
    PageDto<Nave> finAllNaves(final Pageable pageable); 
    List<Nave> findByNombreContainingIgnoreCase(final String name);
    void deleteNave (final UUID id);
    void updateNave(final Nave nave);
}

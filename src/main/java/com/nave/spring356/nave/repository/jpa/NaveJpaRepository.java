package com.nave.spring356.nave.repository.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nave.spring356.nave.models.entity.Nave;

public interface NaveJpaRepository extends JpaRepository<Nave,UUID>{
        
    List<Nave> findByNombreContainingIgnoreCase(final String nombre);
}

package com.nave.spring356.nave.models.responses;

import java.util.UUID;

import lombok.Builder;

public record NaveResponse(
        UUID id,
        String nombre,
        String modelo,
        String empresa,
        Integer sizeTripulacion
) {
    
}

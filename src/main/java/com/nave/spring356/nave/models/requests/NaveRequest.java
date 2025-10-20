package com.nave.spring356.nave.models.requests;

import lombok.Builder;

@Builder
public record NaveRequest(
    String nombre,
    String modelo,
    String empresa,
    Integer sizeTripulacion
) {    
    
}

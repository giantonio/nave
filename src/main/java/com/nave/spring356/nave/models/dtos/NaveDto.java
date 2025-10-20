package com.nave.spring356.nave.models.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaveDto {

    private UUID id;
    private String nombre;
    private String modelo;
    private String empresa;
    private int sizeTripulacion;
    
}

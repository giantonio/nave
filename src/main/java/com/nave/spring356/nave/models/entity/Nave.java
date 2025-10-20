package com.nave.spring356.nave.models.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Nave")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Nave {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Modelo")
    private String modelo;

    @Column(name = "Empresa")
    private String empresa;

    @Column(name = "size_tripulacion")
    private int sizeTripulacion;
     
    
}

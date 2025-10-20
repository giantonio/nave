package com.nave.spring356.nave.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nave.spring356.nave.models.dtos.PageDto;
import com.nave.spring356.nave.models.entity.Nave;
import com.nave.spring356.nave.models.requests.NaveRequest;
import com.nave.spring356.nave.models.responses.NaveResponse;
import com.nave.spring356.nave.service.NaveService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/naves")
@RequiredArgsConstructor
public class NaveController {

    private final NaveService naveService;

    @PostMapping(path = "/NewNave")
    @Operation(summary = "Create new Nave")
    public ResponseEntity<NaveResponse> createNave(@RequestBody NaveRequest nave){
        final NaveResponse result = naveService.createNave(nave);
        return ResponseEntity.ok(result);
        
    }
    
    @GetMapping(path = "/allNaves")
    @Operation(summary = "Get all Naves")
    public ResponseEntity<PageDto<NaveResponse>> getAllNaves(
        @RequestParam(required = false, defaultValue = "0") @Min(0) final int pageNum,
        @RequestParam(required = false, defaultValue = "20") @Min(1) @Max(20) final int pageSize        
    ){
        final PageDto<NaveResponse> result = naveService.getAllNaves(pageNum,pageSize);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get una Nave")
    public ResponseEntity<NaveResponse>getNaveById(@PathVariable final UUID id){
        final NaveResponse result = naveService.getNaveResponse(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path ="/ByName/{name}")
    @Operation(summary = "Get naves by name")
    public ResponseEntity<List<Nave>>getNavesByNombre(@PathVariable(name = "name", required = false) final String nombre){
        final List<Nave> result = naveService.getByNombreContaining(nombre);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a nave")
    public ResponseEntity<Void> deleteNaveById(@PathVariable final UUID id){
        naveService.deleteNave(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path =  "/updateNave")
    @Operation(summary = "Update una nave")
    public ResponseEntity<NaveResponse>updateNave(@RequestBody Nave nave){        
        naveService.updateNave(nave);
        return ResponseEntity.noContent().build();
    }

}

package com.nave.spring356.nave.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.nave.spring356.nave.exceptions.ResourceNotFoundException;
import com.nave.spring356.nave.models.dtos.PageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nave.spring356.nave.mapper.NaveMapper;
import com.nave.spring356.nave.models.entity.Nave;
import com.nave.spring356.nave.models.requests.NaveRequest;
import com.nave.spring356.nave.models.responses.NaveResponse;
import com.nave.spring356.nave.repository.NaveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class NaveServiceTest {

    @Mock
    private NaveRepository naveRepo;

    @Mock
    private NaveMapper mapperInstance;

    @InjectMocks
    private NaveServiceImpl naveService;
   
    private NaveRequest naveRequest;  
    private Nave naveEntity;  
    private NaveResponse naveResponse;
    private PageDto<Nave> navePageDto;

    @BeforeEach
    void setUp() {

        naveRequest = new NaveRequest(
                "X-Wing",
                "Modelo-X",
                "Rebelde",
                2
        );

        naveEntity = Nave.builder()
                .id(UUID.randomUUID())
                .nombre("X-Wing")
                .modelo("Modelo-X")
                .empresa("Rebelde")
                .sizeTripulacion(2)
                .build();

        naveResponse = new NaveResponse(
                naveEntity.getId(),
                naveEntity.getNombre(),
                naveEntity.getModelo(),
                naveEntity.getEmpresa(),
                naveEntity.getSizeTripulacion()
        );

        navePageDto = new PageDto<>(List.of(naveEntity));
    }

    @Test
    void givenValidRequest_whenSaveEntity_shouldReturnValidResponse() {

        when(mapperInstance.requestToEntity(naveRequest)).thenReturn(naveEntity);
        when(naveRepo.save(naveEntity)).thenReturn(naveEntity);
        when(mapperInstance.entityToResponse(naveEntity)).thenReturn(naveResponse);

        NaveResponse result = naveService.createNave(naveRequest);

        // Assert
        assertNotNull(result);
        assertEquals(naveResponse.id(), result.id());
        assertEquals(naveResponse.nombre(), result.nombre());
        assertEquals(naveResponse.modelo(), result.modelo());
        assertEquals(naveResponse.empresa(), result.empresa());
        assertEquals(naveResponse.sizeTripulacion(), result.sizeTripulacion());


        verify(mapperInstance).requestToEntity(naveRequest);
        verify(mapperInstance).entityToResponse(naveEntity);
        verify(naveRepo).save(naveEntity);

    }

    @Test
    void givenValidPageNumAndPageSize_whenReturnAll_shouldReturnValidResponse(){

        int pageNum = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        List<Nave> naveList = List.of(naveEntity);
        Page<Nave> navePage = new PageImpl<>(naveList, pageable, naveList.size());

        when(naveRepo.finAllNaves(any(Pageable.class))).thenReturn(navePageDto);
        when(mapperInstance.entityToResponse(naveEntity)).thenReturn(naveResponse);


        PageDto<NaveResponse> result = naveService.getAllNaves(pageNum, pageSize);


        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals("X-Wing", result.content().get(0).nombre());

        verify(naveRepo).finAllNaves(pageable);
        verify(mapperInstance).entityToResponse(naveEntity);

    }

    @Test
    void givenValidId_whenReturnGetObject_shouldReturnValidResponse(){

        UUID id = naveEntity.getId();

        when(naveRepo.findById(id)).thenReturn(Optional.of(naveEntity));
        when(mapperInstance.entityToResponse(naveEntity)).thenReturn(naveResponse);


        NaveResponse result = naveService.getNaveResponse(id);


        assertNotNull(result);
        assertEquals(naveResponse.id(), result.id());
        assertEquals(naveResponse.nombre(), result.nombre());
        assertEquals(naveResponse.modelo(), result.modelo());
        assertEquals(naveResponse.empresa(), result.empresa());

        verify(naveRepo).findById(id);
        verify(mapperInstance).entityToResponse(naveEntity);
    }

    @Test
    void givenInValid_whenReturnGetObject_shouldThrowResourceNotFoundException(){

        UUID invalidId = UUID.randomUUID();

        when(naveRepo.findById(invalidId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> naveService.getNaveResponse(invalidId)
        );

        assertTrue(exception.getMessage().contains(invalidId.toString()));
        verify(naveRepo).findById(invalidId);

    }

    @Test
    void givenValidName_whenReturnListNaves_shouldReturnList(){

        String name = naveEntity.getNombre();

        List<Nave> naveList = List.of(naveEntity);

        when(naveRepo.findByNombreContainingIgnoreCase(name)).thenReturn(naveList);

        List<Nave> result = naveService.getByNombreContaining(name);

        assertNotNull(result);
        assertNotNull(naveRepo.findByNombreContainingIgnoreCase(name));

    }

    @Test
    void givenValidId_whenReturnNothing_shouldReturnNothing(){

        UUID id = naveEntity.getId();

        when(naveRepo.findById(id)).thenReturn(Optional.of(naveEntity));
        doNothing().when(naveRepo).deleteNave(id);

        naveService.deleteNave(id);

        verify(naveRepo).findById(id);
        verify(naveRepo).deleteNave(id);

    }

    @Test
    void givenInvalidId_whenDeleteNave_shouldThrowException() {

        UUID invalidId = UUID.randomUUID();
        when(naveRepo.findById(invalidId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> naveService.deleteNave(invalidId)
        );

        assertTrue(exception.getMessage().contains(invalidId.toString()));

        verify(naveRepo).findById(invalidId);
        verify(naveRepo, never()).deleteNave(any(UUID.class));
    }

    @Test
    void givenValidEntity_whenReturningUpdatedEntity_shouldReturnUpdated(){

        UUID id = naveEntity.getId();
        Nave updatedNave = Nave.builder()
                .id(id)
                .nombre("X-Wing Updated")
                .modelo("Modelo-XX")
                .empresa("Nueva Empresa")
                .sizeTripulacion(4)
                .build();

        when(naveRepo.findById(id)).thenReturn(Optional.of(naveEntity));
        when(naveRepo.save(any(Nave.class))).thenReturn(naveEntity);

        naveService.updateNave(updatedNave);

        assertEquals("X-Wing Updated", naveEntity.getNombre());
        assertEquals("Modelo-XX", naveEntity.getModelo());
        assertEquals("Nueva Empresa", naveEntity.getEmpresa());
        assertEquals(4, naveEntity.getSizeTripulacion());

        verify(naveRepo).findById(id);
        verify(naveRepo).save(naveEntity);

    }
}

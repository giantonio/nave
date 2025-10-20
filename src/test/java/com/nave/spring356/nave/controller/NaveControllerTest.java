package com.nave.spring356.nave.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nave.spring356.nave.controllers.NaveController;
import com.nave.spring356.nave.models.dtos.PageDto;
import com.nave.spring356.nave.models.entity.Nave;
import com.nave.spring356.nave.models.requests.NaveRequest;
import com.nave.spring356.nave.models.responses.NaveResponse;
import com.nave.spring356.nave.service.NaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = NaveController.class)
@AutoConfigureMockMvc(addFilters = false)
class NaveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NaveService naveService;

    private NaveRequest naveRequest;
    private NaveResponse naveResponse;

    private PageDto<NaveResponse> pageDto;
    private UUID naveId;
    private List<Nave> naves;
    private Nave nave;

    @BeforeEach
    void setUp() {
        naveId = UUID.randomUUID();
        naveRequest = new NaveRequest(
                "X-Wing",
                "Modelo-X",
                "Rebelde",
                2
        );

        naveResponse = new NaveResponse(
                UUID.randomUUID(),
                "X-Wing",
                "Modelo-X",
                "Rebelde",
                2
        );

        NaveResponse nave1 = new NaveResponse(
                UUID.randomUUID(),
                "X-Wing",
                "Modelo-X",
                "Rebelde",
                2
        );

        NaveResponse nave2 = new NaveResponse(
                UUID.randomUUID(),
                "TIE Fighter",
                "Modelo-T",
                "Imperio",
                1
        );

        pageDto = new PageDto<NaveResponse>(List.of(nave1, nave2));

        Nave nave11 = new Nave(UUID.randomUUID(), "X-Wing", "Modelo-X", "Rebelde", 2);
        Nave nave22 = new Nave(UUID.randomUUID(), "X-Wing 2", "Modelo-XX", "Rebelde", 3);
        naves = List.of(nave11, nave22);

        nave = new Nave(
                UUID.randomUUID(),
                "X-Wing",
                "Modelo-X",
                "Rebelde",
                2
        );
    }

    @Test
    void givenValidRequest_whenCreateNave_shouldReturn200AndResponseBody() throws Exception {
        // Arrange
        when(naveService.createNave(any(NaveRequest.class))).thenReturn(naveResponse);

        // Act & Assert
        mockMvc.perform(post("/NewNave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(naveRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(naveResponse.id().toString()))
                .andExpect(jsonPath("$.nombre").value("X-Wing"))
                .andExpect(jsonPath("$.modelo").value("Modelo-X"))
                .andExpect(jsonPath("$.empresa").value("Rebelde"))
                .andExpect(jsonPath("$.sizeTripulacion").value(2));
    }

    @Test
    void givenValidRequest_whenGetAllNaves_shouldReturn200AndResponseBody() throws Exception {
        // Arrange
        when(naveService.getAllNaves(anyInt(), anyInt())).thenReturn(pageDto);

        // Act & Assert
        mockMvc.perform(get("/allNaves")
                        .param("pageNum", "0")
                        .param("pageSize", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].nombre").value("X-Wing"))
                .andExpect(jsonPath("$.content[0].modelo").value("Modelo-X"))
                .andExpect(jsonPath("$.content[0].empresa").value("Rebelde"))
                .andExpect(jsonPath("$.content[1].nombre").value("TIE Fighter"))
                .andExpect(jsonPath("$.content[1].empresa").value("Imperio"));
    }

    @Test
    void givenValidId_whenGetNaveById_shouldReturn200AndResponseBody() throws Exception {
        // Arrange
        when(naveService.getNaveResponse(any(UUID.class))).thenReturn(naveResponse);

        // Act & Assert
        mockMvc.perform(get("/" + naveId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(naveId.toString()))
                .andExpect(jsonPath("$.nombre").value("X-Wing"))
                .andExpect(jsonPath("$.modelo").value("Modelo-X"))
                .andExpect(jsonPath("$.empresa").value("Rebelde"))
                .andExpect(jsonPath("$.sizeTripulacion").value(2));
    }

    @Test
    void givenValidName_whenGetNavesByNombre_shouldReturn200AndList() throws Exception {
        // Arrange
        when(naveService.getByNombreContaining(anyString())).thenReturn(naves);

        // Act & Assert
        mockMvc.perform(get("/ByName/X-Wing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("X-Wing"))
                .andExpect(jsonPath("$[1].nombre").value("X-Wing 2"));
    }

    @Test
    void givenValidId_whenDeleteNave_shouldReturn204AndCallService() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/" + naveId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify
        verify(naveService, times(1)).deleteNave(naveId);
    }

    @Test
    void givenValidNave_whenUpdateNave_shouldReturn204AndCallService() throws Exception {
        // Act & Assert
        mockMvc.perform(put("/updateNave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nave)))
                .andExpect(status().isNoContent());

        // Verify
        verify(naveService, times(1)).updateNave(nave);
    }
















}

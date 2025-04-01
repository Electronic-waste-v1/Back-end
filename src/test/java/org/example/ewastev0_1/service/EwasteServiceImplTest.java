package org.example.ewastev0_1.service;


import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.domain.entites.Enum.Etatwaste;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;
import org.example.ewastev0_1.dto.response.UserResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.EwasteMapper;
import org.example.ewastev0_1.repository.EwasteRepository;
import org.example.ewastev0_1.services.Impl.EwasteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EwasteServiceImplTest {

    @Mock
    private EwasteRepository ewasteRepository;

    @Mock
    private EwasteMapper ewasteMapper;

    @InjectMocks
    private EwasteServiceImpl ewasteService;

    private Ewaste ewaste;
    private EwasteRequest ewasteRequest;
    private EwasteResponse ewasteResponse;
    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1);
        user.setUsername("Test User");

        userResponse = new UserResponse();
        userResponse.setId(1);
        userResponse.setUsername("Test User");

        ewasteRequest = EwasteRequest.builder()
                .nom("Old Laptop")
                .description("Working laptop with minor issues")
                .categorie("Electronics")
                .etat(Etatwaste.Reparable)
                .user_id(1)
                .build();

        ewaste = new Ewaste();
        ewaste.setId(1);
        ewaste.setNom("Old Laptop");
        ewaste.setDescription("Working laptop with minor issues");
        ewaste.setCategorie("Electronics");
        ewaste.setEtat(Etatwaste.Reparable);
        ewaste.setUser(user);
        ewaste.setAnnonce(new ArrayList<>());

        ewasteResponse = EwasteResponse.builder()
                .id(1)
                .nom("Old Laptop")
                .description("Working laptop with minor issues")
                .categorie("Electronics")
                .etat(Etatwaste.Reparable)
                .user(userResponse)
                .annonce(new ArrayList<>())
                .build();
    }

    @Test
    void createEwaste_ShouldReturnValidResponse() {

        when(ewasteMapper.toEntity(any(EwasteRequest.class))).thenReturn(ewaste);
        when(ewasteRepository.save(any(Ewaste.class))).thenReturn(ewaste);
        when(ewasteMapper.toResponse(any(Ewaste.class))).thenReturn(ewasteResponse);


        EwasteResponse result = ewasteService.createEwaste(ewasteRequest);


        assertNotNull(result);
        assertEquals("Old Laptop", result.getNom());
        assertEquals("Electronics", result.getCategorie());
        assertEquals(Etatwaste.Reparable, result.getEtat());

        verify(ewasteMapper).toEntity(ewasteRequest);
        verify(ewasteRepository).save(ewaste);
        verify(ewasteMapper).toResponse(ewaste);
    }

    @Test
    void getEwasteById_WhenExists_ShouldReturnEwaste() {

        when(ewasteRepository.findById(1)).thenReturn(Optional.of(ewaste));
        when(ewasteMapper.toResponse(ewaste)).thenReturn(ewasteResponse);


        EwasteResponse result = ewasteService.getEwasteById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Old Laptop", result.getNom());

        verify(ewasteRepository).findById(1);
        verify(ewasteMapper).toResponse(ewaste);
    }

    @Test
    void getEwasteById_WhenNotExists_ShouldThrowException() {

        when(ewasteRepository.findById(999)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> ewasteService.getEwasteById(999)
        );

        assertTrue(exception.getMessage().contains("E-waste not found with ID: 999"));
        verify(ewasteRepository).findById(999);
        verify(ewasteMapper, never()).toResponse(any());
    }

    @Test
    void updateEwaste_WhenExists_ShouldReturnUpdatedEwaste() {

        EwasteRequest updateRequest = EwasteRequest.builder()
                .nom("Updated Laptop")
                .description("Fully repaired laptop")
                .categorie("Electronics")
                .etat(Etatwaste.Donne)
                .user_id(1)
                .build();

        Ewaste updatedEwaste = new Ewaste();
        updatedEwaste.setId(1);
        updatedEwaste.setNom("Updated Laptop");
        updatedEwaste.setDescription("Fully repaired laptop");
        updatedEwaste.setCategorie("Electronics");
        updatedEwaste.setEtat(Etatwaste.Donne);
        updatedEwaste.setUser(user);

        EwasteResponse updatedResponse = EwasteResponse.builder()
                .id(1)
                .nom("Updated Laptop")
                .description("Fully repaired laptop")
                .categorie("Electronics")
                .etat(Etatwaste.Donne)
                .user(userResponse)
                .build();

        when(ewasteRepository.findById(1)).thenReturn(Optional.of(ewaste));
        doNothing().when(ewasteMapper).updateEntity(ewaste, updateRequest);
        when(ewasteRepository.save(ewaste)).thenReturn(updatedEwaste);
        when(ewasteMapper.toResponse(updatedEwaste)).thenReturn(updatedResponse);

        EwasteResponse result = ewasteService.updateEwaste(1, updateRequest);


        assertNotNull(result);
        assertEquals("Updated Laptop", result.getNom());
        assertEquals("Fully repaired laptop", result.getDescription());
        assertEquals(Etatwaste.Donne, result.getEtat());

        verify(ewasteRepository).findById(1);
        verify(ewasteMapper).updateEntity(ewaste, updateRequest);
        verify(ewasteRepository).save(ewaste);
        verify(ewasteMapper).toResponse(updatedEwaste);
    }

    @Test
    void updateEwaste_WhenNotExists_ShouldThrowException() {

        when(ewasteRepository.findById(999)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> ewasteService.updateEwaste(999, ewasteRequest)
        );

        assertTrue(exception.getMessage().contains("E-waste not found with ID: 999"));
        verify(ewasteRepository).findById(999);
        verify(ewasteMapper, never()).updateEntity(any(), any());
        verify(ewasteRepository, never()).save(any());
    }

    @Test
    void deleteEwaste_WhenExists_ShouldDeleteSuccessfully() {

        when(ewasteRepository.existsById(1)).thenReturn(true);
        doNothing().when(ewasteRepository).deleteById(1);


        ewasteService.deleteEwaste(1);

        verify(ewasteRepository).existsById(1);
        verify(ewasteRepository).deleteById(1);
    }

    @Test
    void deleteEwaste_WhenNotExists_ShouldThrowException() {

        when(ewasteRepository.existsById(999)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> ewasteService.deleteEwaste(999)
        );

        assertTrue(exception.getMessage().contains("E-waste not found with ID: 999"));
        verify(ewasteRepository).existsById(999);
        verify(ewasteRepository, never()).deleteById(any());
    }

    @Test
    void getAllEwastes_ShouldReturnAllEwastes() {

        List<Ewaste> ewasteList = Arrays.asList(ewaste);
        List<EwasteResponse> responseList = Arrays.asList(ewasteResponse);

        when(ewasteRepository.findAll()).thenReturn(ewasteList);
        when(ewasteMapper.toResponse(ewaste)).thenReturn(ewasteResponse);


        List<EwasteResponse> result = ewasteService.getAllEwastes();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Old Laptop", result.get(0).getNom());

        verify(ewasteRepository).findAll();
        verify(ewasteMapper).toResponse(ewaste);
    }

    @Test
    void getEwastesByUser_ShouldReturnUserEwastes() {

        List<Ewaste> userEwastes = Arrays.asList(ewaste);
        List<EwasteResponse> responseList = Arrays.asList(ewasteResponse);

        when(ewasteRepository.findByUserId(1)).thenReturn(userEwastes);
        when(ewasteMapper.toResponse(ewaste)).thenReturn(ewasteResponse);

        List<EwasteResponse> result = ewasteService.getEwastesByUser(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Old Laptop", result.get(0).getNom());
        assertEquals(1, result.get(0).getUser().getId());

        verify(ewasteRepository).findByUserId(1);
        verify(ewasteMapper).toResponse(ewaste);
    }

    @Test
    void searchEwasteByCategory_ShouldReturnCategoryEwastes() {

        List<Ewaste> categoryEwastes = Arrays.asList(ewaste);

        when(ewasteRepository.findByCategorie("Electronics")).thenReturn(categoryEwastes);
        when(ewasteMapper.toResponse(ewaste)).thenReturn(ewasteResponse);


        List<EwasteResponse> result = ewasteService.searchEwasteByCategory("Electronics");


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getCategorie());

        verify(ewasteRepository).findByCategorie("Electronics");
        verify(ewasteMapper).toResponse(ewaste);
    }

    @Test
    void updateEwasteStatus_ShouldReturnNull() {

        assertNull(ewasteService.updateEwasteStatus(1, ewasteRequest));
    }
}
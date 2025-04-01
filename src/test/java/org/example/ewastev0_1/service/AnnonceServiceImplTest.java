package org.example.ewastev0_1.service;


import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.Enum.Etat;
import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.response.AnnonceResponse;
import org.example.ewastev0_1.dto.response.EwasteResponse;
import org.example.ewastev0_1.dto.response.UserResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.AnnonceMapper;
import org.example.ewastev0_1.repository.AnnonceRepository;
import org.example.ewastev0_1.services.Impl.AnnonceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnnonceServiceImplTest {

    @Mock
    private AnnonceRepository annonceRepository;

    @Mock
    private AnnonceMapper annonceMapper;

    @InjectMocks
    private AnnonceServiceImpl annonceService;

    private Annonce annonce;
    private AnnonceRequest annonceRequest;
    private AnnonceResponse annonceResponse;
    private User user;
    private Ewaste ewaste;
    private UserResponse userResponse;
    private EwasteResponse ewasteResponse;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1);
        user.setUsername("Test User");

        ewaste = new Ewaste();
        ewaste.setId(1);
        ewaste.setNom("Old Laptop");
        ewaste.setCategorie("Electronics");

        userResponse = new UserResponse();
        userResponse.setId(1);
        userResponse.setUsername("Test User");

        ewasteResponse = EwasteResponse.builder()
                .id(1)
                .nom("Old Laptop")
                .categorie("Electronics")
                .build();

        annonceRequest = AnnonceRequest.builder()
                .title("Laptop for Sale")
                .description("Slightly used laptop in good condition")
                .prix(250.0)
                .etat(Etat.Disponible)
                .user_id(1)
                .waste_id(1)
                .images(new ArrayList<>())
                .build();

        annonce = new Annonce();
        annonce.setId(1);
        annonce.setTitle("Laptop for Sale");
        annonce.setDescription("Slightly used laptop in good condition");
        annonce.setPrix(250.0);
        annonce.setEtat(Etat.Disponible);
        annonce.setUser(user);
        annonce.setEwaste(ewaste);
        annonce.setDisponibilite(true);

        annonceResponse = AnnonceResponse.builder()
                .id(1)
                .title("Laptop for Sale")
                .description("Slightly used laptop in good condition")
                .prix(250.0)
                .etat(Etat.Disponible)
                .user(userResponse)
                .ewaste(ewasteResponse)
                .images(new ArrayList<>())
                .build();
    }

    @Test
    void createAnnonce_ShouldReturnValidResponse() {

        when(annonceMapper.toEntity(any(AnnonceRequest.class))).thenReturn(annonce);
        when(annonceRepository.save(any(Annonce.class))).thenReturn(annonce);
        when(annonceMapper.toResponse(any(Annonce.class))).thenReturn(annonceResponse);

        AnnonceResponse result = annonceService.createAnnonce(annonceRequest);

        assertNotNull(result);
        assertEquals("Laptop for Sale", result.getTitle());
        assertEquals(250.0, result.getPrix());
        assertEquals(Etat.Disponible, result.getEtat());

        verify(annonceMapper).toEntity(annonceRequest);
        verify(annonceRepository).save(annonce);
        verify(annonceMapper).toResponse(annonce);
    }

    @Test
    void updateAnnonce_WhenExists_ShouldReturnUpdatedAnnonce() {

        AnnonceRequest updateRequest = AnnonceRequest.builder()
                .title("Updated Laptop Sale")
                .description("Well-maintained laptop with new battery")
                .prix(300.0)
                .etat(Etat.Donne)
                .user_id(1)
                .waste_id(1)
                .build();

        Annonce updatedAnnonce = new Annonce();
        updatedAnnonce.setId(1);
        updatedAnnonce.setTitle("Updated Laptop Sale");
        updatedAnnonce.setDescription("Well-maintained laptop with new battery");
        updatedAnnonce.setPrix(300.0);
        updatedAnnonce.setEtat(Etat.Donne);
        updatedAnnonce.setUser(user);
        updatedAnnonce.setEwaste(ewaste);

        AnnonceResponse updatedResponse = AnnonceResponse.builder()
                .id(1)
                .title("Updated Laptop Sale")
                .description("Well-maintained laptop with new battery")
                .prix(300.0)
                .etat(Etat.Donne)
                .user(userResponse)
                .ewaste(ewasteResponse)
                .build();

        when(annonceRepository.findById(1)).thenReturn(Optional.of(annonce));
        doNothing().when(annonceMapper).updateEntity(annonce, updateRequest);
        when(annonceRepository.save(annonce)).thenReturn(updatedAnnonce);
        when(annonceMapper.toResponse(updatedAnnonce)).thenReturn(updatedResponse);


        AnnonceResponse result = annonceService.updateAnnonce(1, updateRequest);

        assertNotNull(result);
        assertEquals("Updated Laptop Sale", result.getTitle());
        assertEquals(300.0, result.getPrix());
        assertEquals(Etat.Donne, result.getEtat());

        verify(annonceRepository).findById(1);
        verify(annonceMapper).updateEntity(annonce, updateRequest);
        verify(annonceRepository).save(annonce);
        verify(annonceMapper).toResponse(updatedAnnonce);
    }

    @Test
    void updateAnnonce_WhenNotExists_ShouldThrowException() {

        when(annonceRepository.findById(999)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> annonceService.updateAnnonce(999, annonceRequest)
        );

        assertTrue(exception.getMessage().contains("Annonce not found with ID: 999"));
        verify(annonceRepository).findById(999);
        verify(annonceMapper, never()).updateEntity(any(), any());
        verify(annonceRepository, never()).save(any());
    }

    @Test
    void deleteAnnonce_WhenExists_ShouldDeleteSuccessfully() {

        when(annonceRepository.existsById(1)).thenReturn(true);
        doNothing().when(annonceRepository).deleteById(1);
        annonceService.deleteAnnonce(1);

        verify(annonceRepository).existsById(1);
        verify(annonceRepository).deleteById(1);
    }

    @Test
    void deleteAnnonce_WhenNotExists_ShouldThrowException() {
        when(annonceRepository.existsById(999)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> annonceService.deleteAnnonce(999)
        );

        assertTrue(exception.getMessage().contains("Annonce not found with ID: 999"));
        verify(annonceRepository).existsById(999);
        verify(annonceRepository, never()).deleteById(999);
    }

    @Test
    void getAnnonceById_WhenExists_ShouldReturnAnnonce() {
         when(annonceRepository.findById(1)).thenReturn(Optional.of(annonce));
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);

        AnnonceResponse result = annonceService.getAnnonceById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Laptop for Sale", result.getTitle());

        verify(annonceRepository).findById(1);
        verify(annonceMapper).toResponse(annonce);
    }

    @Test
    void getAnnonceById_WhenNotExists_ShouldThrowException() {
        when(annonceRepository.findById(999)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> annonceService.getAnnonceById(999)
        );

        assertTrue(exception.getMessage().contains("Annonce not found with ID: 999"));
        verify(annonceRepository).findById(999);
        verify(annonceMapper, never()).toResponse(any());
    }

    @Test
    void getAllAnnonces_ShouldReturnAllAnnonces() {
        List<Annonce> annonceList = Arrays.asList(annonce);

        when(annonceRepository.findAll()).thenReturn(annonceList);
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);

        List<AnnonceResponse> result = annonceService.getAllAnnonces();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Laptop for Sale", result.get(0).getTitle());

        verify(annonceRepository).findAll();
        verify(annonceMapper).toResponse(annonce);
    }

    @Test
    void searchAnnoncesByCategory_ShouldReturnCategoryAnnonces() {

        List<Annonce> categoryAnnonces = Arrays.asList(annonce);

        when(annonceRepository.findByEwasteCategorie("Electronics")).thenReturn(categoryAnnonces);
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);

        List<AnnonceResponse> result = annonceService.searchAnnoncesByCategory("Electronics");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Laptop for Sale", result.get(0).getTitle());
        assertEquals("Electronics", result.get(0).getEwaste().getCategorie());

        verify(annonceRepository).findByEwasteCategorie("Electronics");
        verify(annonceMapper).toResponse(annonce);
    }

    @Test
    void filterAnnoncesByPriceRange_ShouldReturnMatchingAnnonces() {
        List<Annonce> priceRangeAnnonces = Arrays.asList(annonce);
        double minPrice = 200.0;
        double maxPrice = 300.0;

        when(annonceRepository.findByPrixBetween(BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice)))
                .thenReturn(priceRangeAnnonces);
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);

        List<AnnonceResponse> result = annonceService.filterAnnoncesByPriceRange(minPrice, maxPrice);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(250.0, result.get(0).getPrix());

        verify(annonceRepository).findByPrixBetween(BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice));
        verify(annonceMapper).toResponse(annonce);
    }

    @Test
    void filterAnnoncesByCondition_ShouldReturnMatchingAnnonces() {
        List<Annonce> conditionAnnonces = Arrays.asList(annonce);

        when(annonceRepository.findByEtat(Etat.Disponible)).thenReturn(conditionAnnonces);
        when(annonceMapper.toResponse(annonce)).thenReturn(annonceResponse);

        List<AnnonceResponse> result = annonceService.filterAnnoncesByCondition("Disponible");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Etat.Disponible, result.get(0).getEtat());

        verify(annonceRepository).findByEtat(Etat.Disponible);
        verify(annonceMapper).toResponse(annonce);
    }
}
package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.Enum.Etat;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.response.AnnonceResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.AnnonceMapper;
import org.example.ewastev0_1.repository.AnnonceRepository;
import org.example.ewastev0_1.services.Interface.AnnonceService;
import org.example.ewastev0_1.services.Interface.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class AnnonceServiceImpl implements AnnonceService {
    private final AnnonceMapper annonceMapper;
    private final AnnonceRepository annonceRepository;


    @Override
    public AnnonceResponse createAnnonce(AnnonceRequest request) {
        log.info("Creating new annonce with details: {}", request);
        Annonce annonce = annonceMapper.toEntity(request);
        Annonce savedAnnonce = annonceRepository.save(annonce);
        log.info("Annonce created successfully with ID: {}", savedAnnonce.getId());
        return annonceMapper.toResponse(savedAnnonce);
    }

    @Override
    public AnnonceResponse updateAnnonce(Integer id, AnnonceRequest request) {
        log.info("Updating annonce with ID: {} and details: {}", id, request);
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Annonce not found with ID: {}", id);
                    return new ResourceNotFoundException("Annonce not found with ID: " + id);
                });

        annonceMapper.updateEntity(annonce, request);
        Annonce updatedAnnonce = annonceRepository.save(annonce);
        log.info("Annonce updated successfully with ID: {}", updatedAnnonce.getId());
        return annonceMapper.toResponse(updatedAnnonce);
    }

    @Override
    public void deleteAnnonce(Integer id) {
        log.info("Deleting annonce with ID: {}", id);
        if (!annonceRepository.existsById(id)) {
            log.error("Failed to delete. Annonce not found with ID: {}", id);
            throw new ResourceNotFoundException("Annonce not found with ID: " + id);
        }
        annonceRepository.deleteById(id);
        log.info("Annonce deleted successfully with ID: {}", id);
    }

    @Override
    public AnnonceResponse getAnnonceById(Integer id) {
        log.info("Fetching annonce with ID: {}", id);
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Annonce not found with ID: {}", id);
                    return new ResourceNotFoundException("Annonce not found with ID: " + id);
                });
        return annonceMapper.toResponse(annonce);
    }

    @Override
    public List<AnnonceResponse> getAllAnnonces() {
        log.info("Fetching all annonces");
        List<Annonce> annonces = annonceRepository.findAll();
        log.info("Retrieved {} annonces", annonces.size());
        return annonces.stream()
                .map(annonceMapper::toResponse)
                .toList();
    }

    @Override
    public List<AnnonceResponse> searchAnnoncesByCategory(String category) {
        log.info("Searching annonces by category: {}", category);
        List<Annonce> annonces = annonceRepository.findByEwasteCategorie(category);
        log.info("Found {} annonces in category: {}", annonces.size(), category);
        return annonces.stream()
                .map(annonceMapper::toResponse)
                .toList();
    }

    @Override
    public List<AnnonceResponse> filterAnnoncesByPriceRange(double minPrice, double maxPrice) {
        log.info("Filtering annonces by price range: {} - {}", minPrice, maxPrice);
        List<Annonce> annonces = annonceRepository.findByPrixBetween(BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice));
        log.info("Found {} annonces in price range: {} - {}", annonces.size(), minPrice, maxPrice);
        return annonces.stream()
                .map(annonceMapper::toResponse)
                .toList();
    }

    @Override
    public List<AnnonceResponse> filterAnnoncesByCondition(String condition) {
        log.info("Filtering annonces by condition: {}", condition);
        Etat etat = Etat.valueOf(condition);
        List<Annonce> annonces = annonceRepository.findByEtat(etat);
        log.info("Found {} annonces with condition: {}", annonces.size(), condition);
        return annonces.stream()
                .map(annonceMapper::toResponse)
                .toList();
    }
}

package org.example.ewastev0_1.controller;

import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.response.AnnonceResponse;
import org.example.ewastev0_1.services.Interface.AnnonceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/annonces")
@RequiredArgsConstructor
@Slf4j
public class AnnonceController {
    private final AnnonceService annonceService;

    @PostMapping
    public ResponseEntity<AnnonceResponse> createAnnonce(@RequestBody AnnonceRequest request) {
        log.info("Received request to create new annonce");
        AnnonceResponse response = annonceService.createAnnonce(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnonceResponse> updateAnnonce(
            @PathVariable Integer id,
            @RequestBody AnnonceRequest request) {
        log.info("Received request to update annonce with ID: {}", id);
        AnnonceResponse response = annonceService.updateAnnonce(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Integer id) {
        log.info("Received request to delete annonce with ID: {}", id);
        annonceService.deleteAnnonce(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnonceResponse> getAnnonceById(@PathVariable Integer id) {
        log.info("Received request to fetch annonce with ID: {}", id);
        AnnonceResponse response = annonceService.getAnnonceById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AnnonceResponse>> getAllAnnonces() {
        log.info("Received request to fetch all annonces");
        List<AnnonceResponse> responses = annonceService.getAllAnnonces();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<AnnonceResponse>> searchAnnoncesByCategory(@PathVariable String category) {
        log.info("Received request to search annonces by category: {}", category);
        List<AnnonceResponse> responses = annonceService.searchAnnoncesByCategory(category);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/price")
    public ResponseEntity<List<AnnonceResponse>> filterAnnoncesByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        log.info("Received request to filter annonces by price range: {} - {}", minPrice, maxPrice);
        List<AnnonceResponse> responses = annonceService.filterAnnoncesByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/condition/{condition}")
    public ResponseEntity<List<AnnonceResponse>> filterAnnoncesByCondition(@PathVariable String condition) {
        log.info("Received request to filter annonces by condition: {}", condition);
        List<AnnonceResponse> responses = annonceService.filterAnnoncesByCondition(condition);
        return ResponseEntity.ok(responses);
    }
}
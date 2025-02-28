package org.example.ewastev0_1.services.Impl;

import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.response.AnnonceResponse;
import org.example.ewastev0_1.services.Interface.AnnonceService;

import java.util.List;

public class AnnonceServiceImpl implements AnnonceService {
    @Override
    public AnnonceResponse createAnnonce(AnnonceRequest request) {
        return null;
    }

    @Override
    public AnnonceResponse updateAnnonce(Integer id, AnnonceRequest request) {
        return null;
    }

    @Override
    public void deleteAnnonce(Integer id) {

    }

    @Override
    public AnnonceResponse getAnnonceById(Integer id) {
        return null;
    }

    @Override
    public List<AnnonceResponse> getAllAnnonces() {
        return List.of();
    }

    @Override
    public List<AnnonceResponse> searchAnnoncesByCategory(String category) {
        return List.of();
    }

    @Override
    public List<AnnonceResponse> filterAnnoncesByPriceRange(double minPrice, double maxPrice) {
        return List.of();
    }

    @Override
    public List<AnnonceResponse> filterAnnoncesByCondition(String condition) {
        return List.of();
    }
}

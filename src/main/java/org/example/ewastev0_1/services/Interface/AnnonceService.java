package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.response.AnnonceResponse;

import java.util.List;

public interface AnnonceService {
    AnnonceResponse createAnnonce(AnnonceRequest request);
    AnnonceResponse updateAnnonce(Integer id, AnnonceRequest request);
    void deleteAnnonce(Integer id);
    AnnonceResponse getAnnonceById(Integer id);
    List<AnnonceResponse> getAllAnnonces();
    List<AnnonceResponse> searchAnnoncesByCategory(String category);
    List<AnnonceResponse> filterAnnoncesByPriceRange(double minPrice, double maxPrice);
    List<AnnonceResponse> filterAnnoncesByCondition(String condition);
    List<AnnonceResponse> getAnnoncesByUserId(Integer userId);
}
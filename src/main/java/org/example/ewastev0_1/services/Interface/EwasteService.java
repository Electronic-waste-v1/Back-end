package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;

import java.util.List;

public interface EwasteService {


    EwasteResponse createEwaste(EwasteRequest request);


    EwasteResponse getEwasteById(Long id);


    EwasteResponse updateEwaste(Long id, EwasteRequest request);


    void deleteEwaste(Long id);


    List<EwasteResponse> getAllEwastes();


    List<EwasteResponse> getEwastesByUser(Long userId);


    List<EwasteResponse> searchEwasteByCategory(String category);


    EwasteResponse updateEwasteStatus(Long id, EwasteRequest etat);
}

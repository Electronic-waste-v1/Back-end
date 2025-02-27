package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;

import java.util.List;

public interface EwasteService {


    EwasteResponse createEwaste(EwasteRequest request);


    EwasteResponse getEwasteById(Integer id);


    EwasteResponse updateEwaste(Integer id, EwasteRequest request);


    void deleteEwaste(Integer id);


    List<EwasteResponse> getAllEwastes();


    List<EwasteResponse> getEwastesByUser(Integer userId);


    List<EwasteResponse> searchEwasteByCategory(String category);


    EwasteResponse updateEwasteStatus(Integer id, EwasteRequest etat);
}

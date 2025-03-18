package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.dto.request.RecompenseRequest;
import org.example.ewastev0_1.dto.response.RecompenseResponse;

import java.util.List;

public interface RecompenseService {
    RecompenseResponse addRecompense(RecompenseRequest request);
    RecompenseResponse updateRecompense(Integer id, RecompenseRequest request);
    void deleteRecompense(Integer id);
    RecompenseResponse getRecompenseById(Integer id);
    List<RecompenseResponse> getAllRecompenses();
    void assignRecompenseToUser(Integer userId, Integer recompenseId);
    List<RecompenseResponse> getRecompensesByUserId(Integer userId);
}
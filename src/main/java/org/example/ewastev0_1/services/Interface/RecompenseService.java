package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.dto.request.RecompenseRequest;
import org.example.ewastev0_1.dto.response.RecompenseResponse;

import java.util.List;

public interface RecompenseService {
    RecompenseResponse addRecompense(RecompenseRequest request);
    RecompenseResponse updateRecompense(Long id, RecompenseRequest request);
    void deleteRecompense(Long id);
    RecompenseResponse getRecompenseById(Long id);
    List<RecompenseResponse> getAllRecompenses();
    void assignRecompenseToUser(Long userId, Long recompenseId);
    List<RecompenseResponse> getRecompensesByUserId(Long userId);
}
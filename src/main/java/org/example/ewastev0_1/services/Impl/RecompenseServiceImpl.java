package org.example.ewastev0_1.services.Impl;

import org.example.ewastev0_1.dto.request.RecompenseRequest;
import org.example.ewastev0_1.dto.response.RecompenseResponse;
import org.example.ewastev0_1.services.Interface.RecompenseService;

import java.util.List;

public class RecompenseServiceImpl implements RecompenseService {
    @Override
    public RecompenseResponse addRecompense(RecompenseRequest request) {
        return null;
    }

    @Override
    public RecompenseResponse updateRecompense(Long id, RecompenseRequest request) {
        return null;
    }

    @Override
    public void deleteRecompense(Long id) {

    }

    @Override
    public RecompenseResponse getRecompenseById(Long id) {
        return null;
    }

    @Override
    public List<RecompenseResponse> getAllRecompenses() {
        return List.of();
    }

    @Override
    public void assignRecompenseToUser(Long userId, Long recompenseId) {

    }

    @Override
    public List<RecompenseResponse> getRecompensesByUserId(Long userId) {
        return List.of();
    }
}

package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.Recompense;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.domain.entites.UserPoints;
import org.example.ewastev0_1.dto.request.RecompenseRequest;
import org.example.ewastev0_1.dto.response.RecompenseResponse;
import org.example.ewastev0_1.exception.InsufficientPointsException;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.RecompenseMapper;
import org.example.ewastev0_1.repository.RecompenseRepository;
import org.example.ewastev0_1.repository.UserPointsRepository;
import org.example.ewastev0_1.repository.UserRepository;
import org.example.ewastev0_1.services.Interface.RecompenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class RecompenseServiceImpl implements RecompenseService {
    private final RecompenseMapper recompenseMapper;
    private final RecompenseRepository recompenseRepository;
    private final UserRepository userRepository;
    private final UserPointsRepository userPointsRepository;

    @Override
    public RecompenseResponse addRecompense(RecompenseRequest request) {
        log.info("Creating new recompense with details: {}", request);
        Recompense recompense = recompenseMapper.toEntity(request);
        Recompense savedRecompense = recompenseRepository.save(recompense);
        log.info("Recompense created successfully with ID: {}", savedRecompense.getId());
        return recompenseMapper.toResponse(savedRecompense);
    }

    @Override
    public RecompenseResponse updateRecompense(Integer id, RecompenseRequest request) {
        log.info("Updating recompense with ID: {} and details: {}", id, request);
        Recompense recompense = recompenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Recompense not found with ID: {}", id);
                    return new ResourceNotFoundException("Recompense not found with ID: " + id);
                });

        recompenseMapper.updateEntity(recompense, request);
        Recompense updatedRecompense = recompenseRepository.save(recompense);
        log.info("Recompense updated successfully with ID: {}", updatedRecompense.getId());
        return recompenseMapper.toResponse(updatedRecompense);
    }

    @Override
    public void deleteRecompense(Integer id) {
        log.info("Deleting recompense with ID: {}", id);
        if (!recompenseRepository.existsById(id)) {
            log.error("Failed to delete. Recompense not found with ID: {}", id);
            throw new ResourceNotFoundException("Recompense not found with ID: " + id);
        }
        recompenseRepository.deleteById(id);
        log.info("Recompense deleted successfully with ID: {}", id);
    }

    @Override
    public RecompenseResponse getRecompenseById(Integer id) {
        log.info("Fetching recompense with ID: {}", id);
        Recompense recompense = recompenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Recompense not found with ID: {}", id);
                    return new ResourceNotFoundException("Recompense not found with ID: " + id);
                });
        return recompenseMapper.toResponse(recompense);
    }

    @Override
    public List<RecompenseResponse> getAllRecompenses() {
        log.info("Fetching all recompenses");
        List<Recompense> recompenses = recompenseRepository.findAll();
        log.info("Retrieved {} recompenses", recompenses.size());
        return recompenses.stream()
                .map(recompenseMapper::toResponse)
                .toList();
    }

    @Override
    public void assignRecompenseToUser(Integer userId, Integer recompenseId) {
        log.info("Assigning recompense ID: {} to user ID: {}", recompenseId, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new ResourceNotFoundException("User not found with ID: " + userId);
                });

        Recompense recompense = recompenseRepository.findById(recompenseId)
                .orElseThrow(() -> {
                    log.error("Recompense not found with ID: {}", recompenseId);
                    return new ResourceNotFoundException("Recompense not found with ID: " + recompenseId);
                });

        UserPoints userPoints = userPointsRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("User points not found for user ID: {}", userId);
                    return new ResourceNotFoundException("User points not found for user ID: " + userId);
                });

        if (userPoints.getPointsTotal() - userPoints.getPointsUtilises() < recompense.getPointsRequis()) {
            log.error("User ID: {} does not have enough points for recompense ID: {}", userId, recompenseId);
            throw new InsufficientPointsException("User does not have enough points for this reward");
        }

        recompense.setUser(user);
        recompenseRepository.save(recompense);

        userPoints.setPointsUtilises(userPoints.getPointsUtilises() + recompense.getPointsRequis());
        userPointsRepository.save(userPoints);

        log.info("Recompense ID: {} successfully assigned to user ID: {}", recompenseId, userId);
    }

    @Override
    public List<RecompenseResponse> getRecompensesByUserId(Integer userId) {
        log.info("Fetching recompenses for user ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            log.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        List<Recompense> recompenses = recompenseRepository.findByUserId(userId);
        log.info("Retrieved {} recompenses for user ID: {}", recompenses.size(), userId);
        return recompenses.stream()
                .map(recompenseMapper::toResponse)
                .toList();
    }
}

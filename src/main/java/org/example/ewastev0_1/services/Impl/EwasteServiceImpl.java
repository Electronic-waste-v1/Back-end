package org.example.ewastev0_1.services.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.ActionHistorique;
import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.domain.entites.UserPoints;
import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.EwasteMapper;
import org.example.ewastev0_1.repository.ActionHistoriqueRepository;
import org.example.ewastev0_1.repository.EwasteRepository;
import org.example.ewastev0_1.repository.UserPointsRepository;
import org.example.ewastev0_1.repository.UserRepository;
import org.example.ewastev0_1.services.Interface.AnnonceService;
import org.example.ewastev0_1.services.Interface.EwasteService;
import org.example.ewastev0_1.services.Interface.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class EwasteServiceImpl implements EwasteService {
    private final EwasteMapper ewasteMapper;
    private final EwasteRepository ewasteRepository;
    private final UserPointsRepository userPointsRepository;
    private final ActionHistoriqueRepository actionHistoriqueRepository;
    private final NotificationService notificationService;
private final UserRepository userRepository;
    @Override
    public EwasteResponse createEwaste(EwasteRequest request) {
        log.info("Creating new e-waste with details: {}", request);


        Integer userId = request.getUser_id();
        log.info("user _id" ,userId);
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Ewaste ewaste = ewasteMapper.toEntity(request);


        ewaste.setUser(user);


        Ewaste savedEwaste = ewasteRepository.save(ewaste);

        log.info("E-waste created successfully with ID: {}", savedEwaste.getId());
        notificationService.createNotification(
                savedEwaste.getUser().getId(),
                "Your e-waste has been successfully registered."
        );

        calculateAndAssignPoints(savedEwaste.getUser().getId(), "RECYCLE", savedEwaste.getId());

        return ewasteMapper.toResponse(savedEwaste);
    }

    private void calculateAndAssignPoints(Integer userId, String actionType, Integer ewasteId) {
        UserPoints userPoints = userPointsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User points not found for user ID: " + userId));

        int pointsEarned = 0;
        switch (actionType) {
            case "RECYCLE":
                pointsEarned = 10;
                break;
            case "DONATE":
                pointsEarned = 5;
                break;
            case "REPAIR":
                pointsEarned = 7;
                break;
            default:
                log.warn("Unknown action type: {}", actionType);
                return;
        }

        userPoints.setPointsTotal(userPoints.getPointsTotal() + pointsEarned);
        userPointsRepository.save(userPoints);

        ActionHistorique action = new ActionHistorique();
        action.setUser(userPoints.getUser());
        action.setActionType(actionType);
        action.setDescription("Points awarded for " + actionType);
        action.setPointsGagnes(pointsEarned);
        action.setDateAction(LocalDateTime.now());
        action.setEwaste(ewasteRepository.findById(ewasteId).orElse(null));
        actionHistoriqueRepository.save(action);

        log.info("Awarded {} points to user ID: {} for action: {}", pointsEarned, userId, actionType);
    }


    @Override
    public EwasteResponse getEwasteById(Integer id) {
        log.info("Fetching e-waste with ID: {}", id);
        Ewaste ewaste = ewasteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("E-waste not found with ID: {}", id);
                    return new ResourceNotFoundException("E-waste not found with ID: " + id);
                });
        return ewasteMapper.toResponse(ewaste);
    }

    @Override
    public EwasteResponse updateEwaste(Integer id, EwasteRequest request) {
        log.info("Updating e-waste with ID: {} and details: {}", id, request);
        Ewaste ewaste = ewasteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("E-waste not found with ID: {}", id);
                    return new ResourceNotFoundException("E-waste not found with ID: " + id);
                });

        ewasteMapper.updateEntity(ewaste, request);
        Ewaste updatedEwaste = ewasteRepository.save(ewaste);
        log.info("E-waste updated successfully with ID: {}", updatedEwaste.getId());
        return ewasteMapper.toResponse(updatedEwaste);
    }

    @Override
    public void deleteEwaste(Integer id) {
        log.info("Deleting e-waste with ID: {}", id);
        if (!ewasteRepository.existsById(id)) {
            log.error("Failed to delete. E-waste not found with ID: {}", id);
            throw new ResourceNotFoundException("E-waste not found with ID: " + id);
        }
        ewasteRepository.deleteById(id);
        log.info("E-waste deleted successfully with ID: {}", id);
    }

    @Override
    public List<EwasteResponse> getAllEwastes() {
        log.info("Fetching all e-wastes");
        List<Ewaste> ewastes = ewasteRepository.findAll();
        log.info("Retrieved {} e-wastes", ewastes.size());
        return ewastes.stream()
                .map(ewasteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EwasteResponse> getEwastesByUser(Integer userId) {
        log.info("Fetching e-wastes for user ID: {}", userId);
        List<Ewaste> ewastes = ewasteRepository.findByUserId(userId);
        log.info("Retrieved {} e-wastes for user ID: {}", ewastes.size(), userId);
        return ewastes.stream()
                .map(ewasteMapper::toResponse)
                .toList();
    }

    @Override
    public List<EwasteResponse> searchEwasteByCategory(String category) {
        log.info("Searching e-wastes by category: {}", category);
        List<Ewaste> ewastes = ewasteRepository.findByCategorie(category);
        log.info("Found {} e-wastes in category: {}", ewastes.size(), category);
        return ewastes.stream()
                .map(ewasteMapper::toResponse)
                .toList();
    }

    @Override
    public EwasteResponse updateEwasteStatus(Integer id, EwasteRequest etat) {
        return null;
    }

//    @Override
//    public EwasteResponse updateEwasteStatus(Integer id, EwasteRequest statusRequest) {
//        log.info("Updating status for e-waste with ID: {}", id);
//        Ewaste ewaste = ewasteRepository.findById(id)
//                .orElseThrow(() -> {
//                    log.error("E-waste not found with ID: {}", id);
//                    return new ResourceNotFoundException("E-waste not found with ID: " + id);
//                });
//
//
//        ewasteMapper.updateEntity(ewaste, statusRequest);
//        Ewaste updatedEwaste = ewasteRepository.save(ewaste);
//        log.info("E-waste status updated successfully for ID: {}", id);
//        return ewasteMapper.toResponse(updatedEwaste);
//    }
}
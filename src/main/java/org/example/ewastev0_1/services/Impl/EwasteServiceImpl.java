package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.EwasteMapper;
import org.example.ewastev0_1.repository.EwasteRepository;
import org.example.ewastev0_1.services.Interface.AnnonceService;
import org.example.ewastev0_1.services.Interface.EwasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class EwasteServiceImpl implements EwasteService {
    private final EwasteMapper ewasteMapper;
    private final EwasteRepository ewasteRepository;

    @Override
    public EwasteResponse createEwaste(EwasteRequest request) {
        log.info("Creating new e-waste with details: {}", request);
        Ewaste ewaste = ewasteMapper.toEntity(request);
        Ewaste savedEwaste = ewasteRepository.save(ewaste);
        log.info("E-waste created successfully with ID: {}", savedEwaste.getId());
        return ewasteMapper.toResponse(savedEwaste);
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
                .toList();
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
//        // Assuming statusRequest contains only the status field to update
//        ewasteMapper.updateEntity(ewaste, statusRequest);
//        Ewaste updatedEwaste = ewasteRepository.save(ewaste);
//        log.info("E-waste status updated successfully for ID: {}", id);
//        return ewasteMapper.toResponse(updatedEwaste);
//    }
}
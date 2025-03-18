package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.RecyclingCenter;
import org.example.ewastev0_1.dto.request.RecyclingCenterRequest;
import org.example.ewastev0_1.dto.response.RecyclingcenterResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.RecyclingCenterMapper;
import org.example.ewastev0_1.repository.RecyclingCenterRepository;
import org.example.ewastev0_1.services.Interface.RecyclingCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class RecyclingCenterServiceImpl implements RecyclingCenterService {
    private final RecyclingCenterMapper recyclingCenterMapper;
    private final RecyclingCenterRepository recyclingCenterRepository;

    @Override
    public RecyclingcenterResponse addRecyclingCenter(RecyclingCenterRequest request) {
        log.info("Creating new recycling center with details: {}", request);
        RecyclingCenter recyclingCenter = recyclingCenterMapper.toEntity(request);
        RecyclingCenter savedCenter = recyclingCenterRepository.save(recyclingCenter);
        log.info("Recycling center created successfully with ID: {}", savedCenter.getId());
        return recyclingCenterMapper.toResponse(savedCenter);
    }

    @Override
    public RecyclingcenterResponse updateRecyclingCenter(Integer id, RecyclingCenterRequest request) {
        log.info("Updating recycling center with ID: {} and details: {}", id, request);
        RecyclingCenter recyclingCenter = recyclingCenterRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Recycling center not found with ID: {}", id);
                    return new ResourceNotFoundException("Recycling center not found with ID: " + id);
                });

        recyclingCenterMapper.updateEntity(recyclingCenter, request);
        RecyclingCenter updatedCenter = recyclingCenterRepository.save(recyclingCenter);
        log.info("Recycling center updated successfully with ID: {}", updatedCenter.getId());
        return recyclingCenterMapper.toResponse(updatedCenter);
    }

    @Override
    public void deleteRecyclingCenter(Integer id) {
        log.info("Deleting recycling center with ID: {}", id);
        if (!recyclingCenterRepository.existsById(id)) {
            log.error("Failed to delete. Recycling center not found with ID: {}", id);
            throw new ResourceNotFoundException("Recycling center not found with ID: " + id);
        }
        recyclingCenterRepository.deleteById(id);
        log.info("Recycling center deleted successfully with ID: {}", id);
    }

    @Override
    public RecyclingcenterResponse getRecyclingCenterById(Integer id) {
        log.info("Fetching recycling center with ID: {}", id);
        RecyclingCenter recyclingCenter = recyclingCenterRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Recycling center not found with ID: {}", id);
                    return new ResourceNotFoundException("Recycling center not found with ID: " + id);
                });
        return recyclingCenterMapper.toResponse(recyclingCenter);
    }

    @Override
    public Page<RecyclingcenterResponse> getAllRecyclingCenters(int page, int size) {
        log.info("Fetching all recycling centers with page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<RecyclingCenter> centers = recyclingCenterRepository.findAll(pageable);
        log.info("Retrieved {} recycling centers", centers.getNumberOfElements());
        return centers.map(recyclingCenterMapper::toResponse);
    }

    @Override
    public List<RecyclingcenterResponse> searchRecyclingCentersByLocation(String location) {
        log.info("Searching recycling centers by location: {}", location);
        List<RecyclingCenter> centers = recyclingCenterRepository.findByAdresseContainingIgnoreCase(location);
        log.info("Found {} recycling centers in location: {}", centers.size(), location);
        return centers.stream()
                .map(recyclingCenterMapper::toResponse)
                .toList();
    }

    @Override
    public List<RecyclingcenterResponse> filterRecyclingCentersByAcceptedDevices(String deviceType) {
        log.info("Filtering recycling centers by accepted devices: {}", deviceType);
        List<RecyclingCenter> centers = recyclingCenterRepository.findByAcceptedTypesContainingIgnoreCase(deviceType);
        log.info("Found {} recycling centers accepting device type: {}", centers.size(), deviceType);
        return centers.stream()
                .map(recyclingCenterMapper::toResponse)
                .toList();
    }
}

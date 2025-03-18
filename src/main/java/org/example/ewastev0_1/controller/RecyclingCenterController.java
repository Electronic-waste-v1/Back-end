package org.example.ewastev0_1.controller;

import org.example.ewastev0_1.dto.request.RecyclingCenterRequest;
import org.example.ewastev0_1.dto.response.RecyclingcenterResponse;
import org.example.ewastev0_1.services.Interface.RecyclingCenterService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/recycling-centers")
@RequiredArgsConstructor
@Slf4j
public class RecyclingCenterController {
    private final RecyclingCenterService recyclingCenterService;

    @PostMapping
    public ResponseEntity<RecyclingcenterResponse> addRecyclingCenter(@RequestBody RecyclingCenterRequest request) {
        log.info("Received request to create new recycling center");
        RecyclingcenterResponse response = recyclingCenterService.addRecyclingCenter(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecyclingcenterResponse> updateRecyclingCenter(
            @PathVariable Integer id,
            @RequestBody RecyclingCenterRequest request) {
        log.info("Received request to update recycling center with ID: {}", id);
        RecyclingcenterResponse response = recyclingCenterService.updateRecyclingCenter(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecyclingCenter(@PathVariable Integer id) {
        log.info("Received request to delete recycling center with ID: {}", id);
        recyclingCenterService.deleteRecyclingCenter(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecyclingcenterResponse> getRecyclingCenterById(@PathVariable Integer id) {
        log.info("Received request to fetch recycling center with ID: {}", id);
        RecyclingcenterResponse response = recyclingCenterService.getRecyclingCenterById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<RecyclingcenterResponse>> getAllRecyclingCenters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        log.info("Received request to fetch all recycling centers with page={}, size={}", page, size);
        Page<RecyclingcenterResponse> responses = recyclingCenterService.getAllRecyclingCenters(page, size);
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/location")
    public ResponseEntity<List<RecyclingcenterResponse>> searchRecyclingCentersByLocation(@RequestParam String location) {
        log.info("Received request to search recycling centers by location: {}", location);
        List<RecyclingcenterResponse> responses = recyclingCenterService.searchRecyclingCentersByLocation(location);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/devices")
    public ResponseEntity<List<RecyclingcenterResponse>> filterRecyclingCentersByAcceptedDevices(@RequestParam String deviceType) {
        log.info("Received request to filter recycling centers by accepted devices: {}", deviceType);
        List<RecyclingcenterResponse> responses = recyclingCenterService.filterRecyclingCentersByAcceptedDevices(deviceType);
        return ResponseEntity.ok(responses);
    }
}
package org.example.ewastev0_1.controller;

import org.example.ewastev0_1.dto.request.RecompenseRequest;
import org.example.ewastev0_1.dto.response.RecompenseResponse;
import org.example.ewastev0_1.services.Interface.RecompenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/recompenses")
@RequiredArgsConstructor
@Slf4j
public class RecompenseController {
    private final RecompenseService recompenseService;

    @PostMapping
    public ResponseEntity<RecompenseResponse> addRecompense(@RequestBody RecompenseRequest request) {
        log.info("Received request to create new recompense");
        RecompenseResponse response = recompenseService.addRecompense(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecompenseResponse> updateRecompense(
            @PathVariable Integer id,
            @RequestBody RecompenseRequest request) {
        log.info("Received request to update recompense with ID: {}", id);
        RecompenseResponse response = recompenseService.updateRecompense(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecompense(@PathVariable Integer id) {
        log.info("Received request to delete recompense with ID: {}", id);
        recompenseService.deleteRecompense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecompenseResponse> getRecompenseById(@PathVariable Integer id) {
        log.info("Received request to fetch recompense with ID: {}", id);
        RecompenseResponse response = recompenseService.getRecompenseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RecompenseResponse>> getAllRecompenses() {
        log.info("Received request to fetch all recompenses");
        List<RecompenseResponse> responses = recompenseService.getAllRecompenses();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignRecompenseToUser(
            @RequestParam Integer userId,
            @RequestParam Integer recompenseId) {
        log.info("Received request to assign recompense ID: {} to user ID: {}", recompenseId, userId);
        recompenseService.assignRecompenseToUser(userId, recompenseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecompenseResponse>> getRecompensesByUserId(@PathVariable Integer userId) {
        log.info("Received request to fetch recompenses for user ID: {}", userId);
        List<RecompenseResponse> responses = recompenseService.getRecompensesByUserId(userId);
        return ResponseEntity.ok(responses);
    }
}
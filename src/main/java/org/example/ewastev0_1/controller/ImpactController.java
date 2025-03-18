package org.example.ewastev0_1.controller;

import org.example.ewastev0_1.dto.response.ImpactResponse;
import org.example.ewastev0_1.services.Interface.ImpactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/impact")
@RequiredArgsConstructor
@Slf4j
public class ImpactController {
    private final ImpactService impactService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ImpactResponse> calculateUserImpact(@PathVariable Integer userId) {
        log.info("Received request to calculate impact for user ID: {}", userId);
        ImpactResponse response = impactService.calculateImpact(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total")
    public ResponseEntity<ImpactResponse> getTotalSystemImpact() {
        log.info("Received request to calculate total system impact");
        ImpactResponse response = impactService.getTotalImpact();
        return ResponseEntity.ok(response);
    }
}
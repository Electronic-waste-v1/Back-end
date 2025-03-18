package org.example.ewastev0_1.controller;

import org.example.ewastev0_1.dto.response.EnvironmentalImpactResponse;
import org.example.ewastev0_1.dto.response.LeaderboardResponse;
import org.example.ewastev0_1.dto.response.UserStatisticsResponse;
import org.example.ewastev0_1.services.Interface.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/environmental-impact/{userId}")
    public ResponseEntity<EnvironmentalImpactResponse> calculateEnvironmentalImpact(@PathVariable Integer userId) {
        log.info("Received request to calculate environmental impact for user ID: {}", userId);
        EnvironmentalImpactResponse response = statisticsService.calculateEnvironmentalImpact(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserStatisticsResponse> getUserStatistics(@PathVariable Integer userId) {
        log.info("Received request to retrieve user statistics for ID: {}", userId);
        UserStatisticsResponse response = statisticsService.getUserStatistics(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<LeaderboardResponse> getLeaderboard() {
        log.info("Received request to fetch leaderboard");
        LeaderboardResponse response = statisticsService.getLeaderboard();
        return ResponseEntity.ok(response);
    }
}
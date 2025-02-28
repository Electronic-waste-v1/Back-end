package org.example.ewastev0_1.services.Interface;


import org.example.ewastev0_1.dto.response.EnvironmentalImpactResponse;
import org.example.ewastev0_1.dto.response.LeaderboardResponse;
import org.example.ewastev0_1.dto.response.UserStatisticsResponse;

public interface StatisticsService {
    EnvironmentalImpactResponse calculateEnvironmentalImpact(Long userId);
    UserStatisticsResponse getUserStatistics(Long userId);
    LeaderboardResponse getLeaderboard();
}

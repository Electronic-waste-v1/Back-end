package org.example.ewastev0_1.services.Interface;


import org.example.ewastev0_1.dto.response.EnvironmentalImpactResponse;
import org.example.ewastev0_1.dto.response.LeaderboardResponse;
import org.example.ewastev0_1.dto.response.UserStatisticsResponse;

public interface StatisticsService {
    EnvironmentalImpactResponse calculateEnvironmentalImpact(Integer userId);
    UserStatisticsResponse getUserStatistics(Integer userId);
    LeaderboardResponse getLeaderboard();
}

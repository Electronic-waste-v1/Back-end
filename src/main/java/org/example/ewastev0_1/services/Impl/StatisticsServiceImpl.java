package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.ActionHistorique;
import org.example.ewastev0_1.domain.entites.Enum.Etatwaste;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.domain.entites.UserPoints;
import org.example.ewastev0_1.dto.response.EnvironmentalImpactResponse;
import org.example.ewastev0_1.dto.response.LeaderboardEntryResponse;
import org.example.ewastev0_1.dto.response.LeaderboardResponse;
import org.example.ewastev0_1.dto.response.UserStatisticsResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.repository.ActionHistoriqueRepository;
import org.example.ewastev0_1.repository.EwasteRepository;
import org.example.ewastev0_1.repository.UserPointsRepository;
import org.example.ewastev0_1.repository.UserRepository;
import org.example.ewastev0_1.services.Interface.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class StatisticsServiceImpl implements StatisticsService {
    private final UserRepository userRepository;
    private final EwasteRepository ewasteRepository;
    private final ActionHistoriqueRepository actionHistoriqueRepository;
    private final UserPointsRepository userPointsRepository;

    @Override
    public EnvironmentalImpactResponse calculateEnvironmentalImpact(Integer userId) {
        log.info("Calculating environmental impact for user ID: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }


        List<ActionHistorique> actions = actionHistoriqueRepository.findByUser_IdAndActionType(userId, Etatwaste.Recycler.name());


        double co2Reduction = actions.size() * 5.2;


        double energySaved = actions.size() * 10.5;


        double rawMaterialsSaved = actions.size() * 0.8;

        log.info("Environmental impact calculated for user ID: {}", userId);

        return EnvironmentalImpactResponse.builder()
                .userId(userId)
                .co2Reduction(co2Reduction)
                .energySaved(energySaved)
                .rawMaterialsSaved(rawMaterialsSaved)
                .recycledDevicesCount(actions.size())
                .build();
    }



    @Override
    public UserStatisticsResponse getUserStatistics(Integer userId) {
        log.info("Retrieving statistics for user ID: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }


        long recycledCount = ewasteRepository.countByUserIdAndEtat(userId, Etatwaste.Recycler);



        long donatedCount = ewasteRepository.countByUserIdAndEtat(userId, Etatwaste.Donne);



        long repairedCount = ewasteRepository.countByUserIdAndEtat(userId, Etatwaste.Reparable);



        UserPoints userPoints = userPointsRepository.findByUserId(userId)
                .orElse(UserPoints.builder().pointsTotal(0).pointsUtilises(0).build());


        long userRank = userPointsRepository.countByPointsTotalGreaterThan(userPoints.getPointsTotal()) + 1;

        log.info("Statistics retrieved for user ID: {}", userId);

        return UserStatisticsResponse.builder()
                .userId(userId)
                .recycledDevicesCount(recycledCount)
                .donatedDevicesCount(donatedCount)
                .repairedDevicesCount(repairedCount)
                .totalPoints(userPoints.getPointsTotal())
                .availablePoints(userPoints.getPointsTotal() - userPoints.getPointsUtilises())
                .userRanking(userRank)
                .build();
    }

    @Override
    public LeaderboardResponse getLeaderboard() {
        log.info("Generating leaderboard");


        List<UserPoints> topUsers = userPointsRepository.findTop10ByOrderByPointsTotalDesc();

        List<LeaderboardEntryResponse> entries = topUsers.stream()
                .map(points -> {
                    User user = points.getUser();
                    return LeaderboardEntryResponse.builder()
                            .userId(user.getId())
                            .userName(user.getUsername())
                            .points(points.getPointsTotal())
                            .recycledCount(ewasteRepository.countByUserIdAndEtat(user.getId(), Etatwaste.Recycler))
                            .build();
                })
                .toList();

        log.info("Leaderboard generated with {} entries", entries.size());

        return LeaderboardResponse.builder()
                .entries(entries)
                .generatedAt(LocalDateTime.now())
                .build();
    }
}

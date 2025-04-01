package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.ActionHistorique;
import org.example.ewastev0_1.domain.entites.Enum.Etatwaste;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.dto.response.ImpactResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.ImpactMapper;
import org.example.ewastev0_1.repository.ActionHistoriqueRepository;
import org.example.ewastev0_1.repository.UserRepository;
import org.example.ewastev0_1.services.Interface.ImpactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class ImpactServiceImpl implements ImpactService {
    private final ActionHistoriqueRepository actionHistoriqueRepository;
    private final UserRepository userRepository;

    @Override
    public ImpactResponse calculateImpact(Integer userId) {
        log.info("Calculating environmental impact for user ID: {}", userId);

        if (!userRepository.existsById(Integer.valueOf(userId))) {
            log.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }


        List<ActionHistorique> actions = actionHistoriqueRepository.findByUser_IdAndActionType(userId, "RECYCLE");

        log.info(actions+"actions");
        double co2Reduction = actions.size() * 5.2;


        double waterSaved = actions.size() * 1000;


        double materialsRecovered = actions.size() * 0.5;

        log.info("Impact calculated for user ID: {}. CO2: {} kg, Water: {} L, Materials: {} kg",
                userId, co2Reduction, waterSaved, materialsRecovered);

        return ImpactResponse.builder()
                .userId(userId)
                .co2Reduction(co2Reduction)
                .waterSaved(waterSaved)
                .materialsRecovered(materialsRecovered)
                .recycledDevicesCount(actions.size())
                .build();
    }

    @Override
    public ImpactResponse getTotalImpact() {
        log.info("Calculating total environmental impact across all users");


        List<ActionHistorique> allActions = actionHistoriqueRepository.findByActionType("RECYCLE");

        log.info( "Acton "+ allActions);


        double co2Reduction = allActions.size() * 5.2;
        double waterSaved = allActions.size() * 1000;
        double materialsRecovered = allActions.size() * 0.5;

        log.info("Total system impact calculated. CO2: {} kg, Water: {} L, Materials: {} kg",
                co2Reduction, waterSaved, materialsRecovered);

        return ImpactResponse.builder()
                .userId(null)
                .co2Reduction(co2Reduction)
                .waterSaved(waterSaved)
                .materialsRecovered(materialsRecovered)
                .recycledDevicesCount(allActions.size())
                .build();
    }
}

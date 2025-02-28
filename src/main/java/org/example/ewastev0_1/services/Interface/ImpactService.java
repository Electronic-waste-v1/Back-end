package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.dto.response.ImpactResponse;

public interface ImpactService {
    ImpactResponse calculateImpact(Integer userId);
    ImpactResponse getTotalImpact();
}
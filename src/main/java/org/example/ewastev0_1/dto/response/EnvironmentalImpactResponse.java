package org.example.ewastev0_1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnvironmentalImpactResponse {
    private Long userId;
    private double co2Saved;
    private double wasteReduced;
    private int devicesRecycled;
}

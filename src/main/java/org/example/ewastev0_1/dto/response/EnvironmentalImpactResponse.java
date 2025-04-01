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
    private Integer userId;
    private Double co2Reduction;
    private Double energySaved;
    private Double rawMaterialsSaved;
    private Integer recycledDevicesCount;
}

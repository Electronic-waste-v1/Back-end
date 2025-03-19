package org.example.ewastev0_1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImpactResponse {
    private Integer userId;
    private Double co2Reduction; // Total CO2 reduction in kg
    private Double waterSaved; // Total water saved in liters
    private Double materialsRecovered; // Total materials recovered in kg
    private Integer recycledDevicesCount;
}
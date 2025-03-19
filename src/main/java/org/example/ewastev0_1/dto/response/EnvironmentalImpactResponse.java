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
    private Integer userId; // ID of the user
    private Double co2Reduction; // Total CO2 reduction in kg
    private Double energySaved; // Total energy saved in kWh
    private Double rawMaterialsSaved; // Total raw materials saved in kg
    private Integer recycledDevicesCount; //
}

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
    private Double co2Reduction;
    private Double waterSaved;
    private Double materialsRecovered;
    private Integer recycledDevicesCount;
}
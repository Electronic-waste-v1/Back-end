package org.example.ewastev0_1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatisticsResponse {
    private Integer userId;
    private Long recycledDevicesCount;
    private Long donatedDevicesCount;
    private Long repairedDevicesCount;
    private Integer totalPoints;
    private Integer availablePoints;
    private Long userRanking;
}
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
    private Integer userId; // ID of the user
    private Long recycledDevicesCount; // Number of devices recycled by the user
    private Long donatedDevicesCount; // Number of devices donated by the user
    private Long repairedDevicesCount; // Number of devices repaired by the user
    private Integer totalPoints; // Total points earned by the user
    private Integer availablePoints; // Available points (total points - used points)
    private Long userRanking;
}
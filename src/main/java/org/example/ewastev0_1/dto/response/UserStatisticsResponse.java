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
    private Long userId;
    private int devicesRecycled;
    private int devicesDonated;
    private int pointsEarned;
    private double totalCo2Saved;
    private double totalWasteReduced;
}
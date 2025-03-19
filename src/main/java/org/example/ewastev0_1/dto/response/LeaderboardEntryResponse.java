package org.example.ewastev0_1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardEntryResponse {
    private Integer userId; // ID of the user
    private String userName; // Username of the user
    private Integer points; // Total points of the user
    private Long recycledCount; // Number of devices recycled by the user
}
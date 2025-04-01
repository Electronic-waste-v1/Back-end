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
    private Integer userId;
    private String userName;
    private Integer points;
    private Long recycledCount;
}
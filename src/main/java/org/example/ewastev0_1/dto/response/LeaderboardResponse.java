package org.example.ewastev0_1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardResponse {
    private List<LeaderboardEntryResponse> entries; // List of leaderboard entries
    private LocalDateTime generatedAt; // Timestamp when the leaderboard was generated
}
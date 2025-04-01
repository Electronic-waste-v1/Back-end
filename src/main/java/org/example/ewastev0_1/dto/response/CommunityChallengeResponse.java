package org.example.ewastev0_1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityChallengeResponse {
    
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String imageUrl;
    private String reward;
    private Integer targetGoal;
    private Integer currentProgress;
    private Integer participantsCount;
    private UserSummaryResponse creator;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isParticipatingByCurrentUser;
}


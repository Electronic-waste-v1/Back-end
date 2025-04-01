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
public class CommunityEventResponse {
    
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String imageUrl;
    private UserSummaryResponse organizer;
    private Integer attendeesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isAttendingByCurrentUser;
}


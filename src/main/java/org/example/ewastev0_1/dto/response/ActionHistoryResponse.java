package org.example.ewastev0_1.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActionHistoryResponse {
    private Integer id;
    private Integer userId;
    private String username;
    private String actionType;
    private String description;
    private Integer pointsGained;
    private LocalDateTime actionDate;
    private EwasteResponse ewaste;
}
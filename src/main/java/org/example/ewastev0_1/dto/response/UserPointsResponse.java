package org.example.ewastev0_1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPointsResponse {
    private Integer id;
    private Integer userId;
    private String username;
    private Integer pointsTotal;
    private Integer pointsUtilises;
    private Integer pointsAvailable;
}

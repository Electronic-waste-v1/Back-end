package org.example.ewastev0_1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryResponse {
    
    private Integer id;
    private String username;
    private String avatarUrl;
    private String badge;
}


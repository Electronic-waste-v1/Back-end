package org.example.ewastev0_1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecompenseResponse {
    private int id;
    private String description;
    private Integer pointsRequis;
    private UserResponse userResponse;
}
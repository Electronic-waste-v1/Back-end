package org.example.ewastev0_1.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecompenseRequest {


    private String description;
    private  Integer pointsRequis;

}

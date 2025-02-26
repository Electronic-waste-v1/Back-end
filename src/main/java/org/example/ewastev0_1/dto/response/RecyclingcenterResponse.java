package org.example.ewastev0_1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecyclingcenterResponse {

    private int id;
    private String contact;
    private String nom;
    private String adresse;
    private List<String> acceptedTypes;
}

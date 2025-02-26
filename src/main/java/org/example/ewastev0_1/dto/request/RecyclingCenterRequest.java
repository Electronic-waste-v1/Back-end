package org.example.ewastev0_1.dto.request;


import jakarta.persistence.ElementCollection;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecyclingCenterRequest {

    private String contact;
    private String nom;
    private String adresse;
    private List<String> acceptedTypes;
}

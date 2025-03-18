package org.example.ewastev0_1.dto.request;


import jakarta.persistence.ElementCollection;
import lombok.*;
import org.example.ewastev0_1.domain.entites.Coordinates;
import org.example.ewastev0_1.domain.entites.Enum.Status;

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
        private Coordinates coordinates;
        private Status status;
        private String hours;
        private String description;
        private String phone;
        private String email;
        private String image;
    }
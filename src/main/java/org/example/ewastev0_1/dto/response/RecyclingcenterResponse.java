package org.example.ewastev0_1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ewastev0_1.domain.entites.Coordinates;
import org.example.ewastev0_1.domain.entites.Enum.Status;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecyclingcenterResponse {
    private Integer id;
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
    private String distance;
    private Double rating;
    private LocalDateTime lastVisited;
}
package org.example.ewastev0_1.domain.entites;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class Coordinates {
    private Double lat;
    private Double lng;
}
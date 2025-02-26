package org.example.ewastev0_1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ewastev0_1.domain.entites.Enum.Etat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnonceResponse {

    private int id;
    private String title;
    private String description;
    private Double prix;
    private Etat etat;
    private UserResponse user;
    private EwasteResponse ewaste;
}

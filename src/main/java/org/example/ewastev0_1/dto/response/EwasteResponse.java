package org.example.ewastev0_1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ewastev0_1.domain.entites.Enum.Etat;
import org.example.ewastev0_1.domain.entites.Enum.Etatwaste;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EwasteResponse {
    private int id;
    private String nom;
    private String description;
    private String categorie;
    private Etatwaste etat;
    private UserResponse user;

    private List<AnnonceRes> annonce;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AnnonceRes {
        private String title;
        private String description;
        private Double prix;

        private Etat etat;
    }
}

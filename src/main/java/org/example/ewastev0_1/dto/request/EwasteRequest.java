package org.example.ewastev0_1.dto.request;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.Enum.Etat;
import org.example.ewastev0_1.domain.entites.Enum.Etatwaste;
import org.example.ewastev0_1.domain.entites.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EwasteRequest {
    private String nom;
    private String description;
    private String categorie;

    private Etatwaste etat;

    private Integer user_id;

}

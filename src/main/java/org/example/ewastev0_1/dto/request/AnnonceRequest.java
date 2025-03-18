package org.example.ewastev0_1.dto.request;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ewastev0_1.domain.entites.Enum.Etat;
import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.domain.entites.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnonceRequest {

    private String title;
    private String description;
    private Double prix;

    private Etat etat;

    private Integer user_id;


    private Integer waste_id;
    private transient List<MultipartFile> images;
}

package org.example.ewastev0_1.domain.entites;


import jakarta.persistence.*;
import lombok.*;
import org.example.ewastev0_1.domain.entites.Enum.Etatwaste;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Entity
public class Ewaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private String categorie;
    @Enumerated(EnumType.STRING)
    private Etatwaste etat;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    private List<Annonce> annonce;
}

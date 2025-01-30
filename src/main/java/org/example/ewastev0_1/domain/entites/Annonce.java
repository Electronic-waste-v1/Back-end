package org.example.ewastev0_1.domain.entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ewastev0_1.domain.entites.Enum.Etat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private Double prix;
    @Enumerated(EnumType.STRING)
    private Etat etat;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "waste_id")
    private Ewaste ewaste;


}

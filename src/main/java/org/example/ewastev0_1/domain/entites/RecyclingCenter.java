package org.example.ewastev0_1.domain.entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter

@Table(name = "recycling_center")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecyclingCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    @ElementCollection
    @CollectionTable(name = "accepted_types", joinColumns = @JoinColumn(name = "center_id"))
    @Column(name = "type")
    private List<String> acceptedTypes;
}

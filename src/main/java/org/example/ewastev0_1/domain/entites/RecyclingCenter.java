package org.example.ewastev0_1.domain.entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ewastev0_1.domain.entites.Enum.Status;

import java.time.LocalDateTime;
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
    private Integer id;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    @ElementCollection
    @CollectionTable(name = "collection_point_accepted_types", joinColumns = @JoinColumn(name = "collection_point_id"))
    @Column(name = "type")
    private List<String> acceptedTypes;

    @Embedded
    private Coordinates coordinates;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private String hours;

    @Column(length = 1000)
    private String description;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String image;

    @Transient
    private String distance;

    @Column
    private Double rating;

    @Column
    private LocalDateTime lastVisited;
}

package org.example.ewastev0_1.domain.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "actions_historique")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActionHistorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "action_type")
    private String actionType;

    private String description;
    private Integer pointsGagnes;
    private LocalDateTime dateAction;

    @ManyToOne
    @JoinColumn(name = "ewaste_id")
    private Ewaste ewaste;


}

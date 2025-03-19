package org.example.ewastev0_1.domain.entites;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private String  note;
    @Column(nullable = false)
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User toUser;
}

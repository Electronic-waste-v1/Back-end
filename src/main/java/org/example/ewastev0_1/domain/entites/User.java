package org.example.ewastev0_1.domain.entites;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Annonce> annonces;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Recompense> recompenses;

}
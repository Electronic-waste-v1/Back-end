package org.example.ewastev0_1.domain.entites;


import jakarta.persistence.*;

import lombok.*;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recompense> recompenses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ewaste> ewastes ;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications ;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserPoints userPoints;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

}
package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.Enum.Etatwaste;
import org.example.ewastev0_1.domain.entites.Ewaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EwasteRepository extends JpaRepository<Ewaste, Integer> {
    List<Ewaste> findByUserId(Integer user_id);


    List<Ewaste> findByCategorie(String category);

    long countByUserIdAndEtat(Integer userId, Etatwaste etat);
}

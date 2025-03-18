package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.Recompense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecompenseRepository extends JpaRepository<Recompense, Integer> {
    List<Recompense> findByUserId(Integer userId);
}

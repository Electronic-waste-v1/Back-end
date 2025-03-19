package org.example.ewastev0_1.repository;


import org.example.ewastev0_1.domain.entites.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPointsRepository  extends JpaRepository<UserPoints, Integer> {
    Optional<UserPoints> findByUserId(Integer userId);

    long countByPointsTotalGreaterThan(Integer pointsTotal);

    List<UserPoints> findTop10ByOrderByPointsTotalDesc();
}

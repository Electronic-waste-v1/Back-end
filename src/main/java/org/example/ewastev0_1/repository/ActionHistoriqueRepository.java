package org.example.ewastev0_1.repository;


import org.example.ewastev0_1.domain.entites.ActionHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionHistoriqueRepository extends JpaRepository<ActionHistorique, Integer> {
    List<ActionHistorique> findByUser_IdAndActionType(Integer userId, String actionType);

    List<ActionHistorique> findByUser_Id(Integer userId);
    List<ActionHistorique> findByActionType(String actionType);

}

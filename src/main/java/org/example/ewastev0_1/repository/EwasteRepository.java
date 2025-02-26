package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.Ewaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EwasteRepository extends JpaRepository<Ewaste, Integer> {

}

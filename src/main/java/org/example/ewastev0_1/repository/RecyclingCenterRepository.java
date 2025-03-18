package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.RecyclingCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecyclingCenterRepository extends JpaRepository<RecyclingCenter, Integer> {

    List<RecyclingCenter> findByAdresseContainingIgnoreCase(String location);


    List<RecyclingCenter> findByAcceptedTypesContainingIgnoreCase(String deviceType);
}

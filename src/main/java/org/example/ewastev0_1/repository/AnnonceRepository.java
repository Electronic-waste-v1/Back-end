package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.Enum.Etat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {

    List<Annonce> findByEwasteCategorie(String category);

    List<Annonce> findByPrixBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Annonce> findByEtat(Etat condition);
}

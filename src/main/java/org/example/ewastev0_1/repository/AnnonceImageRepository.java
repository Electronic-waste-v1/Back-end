package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.AnnonceImage;

import java.util.List;

public interface AnnonceImageRepository {
    List<AnnonceImage> findByAnnonceId(Integer annonceId);
}

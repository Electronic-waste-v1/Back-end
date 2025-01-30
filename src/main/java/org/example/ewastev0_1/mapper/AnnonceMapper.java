package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.response.AnnonceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnnonceMapper {

    Annonce toEntity(AnnonceRequest annonceRequest);
    AnnonceResponse toResponse(Annonce annonce);
}

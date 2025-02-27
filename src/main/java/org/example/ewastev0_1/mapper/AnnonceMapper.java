package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.response.AnnonceResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AnnonceMapper {

    Annonce toEntity(AnnonceRequest annonceRequest);
    AnnonceResponse toResponse(Annonce annonce);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Annonce annonce, AnnonceRequest annonceRequest);

}

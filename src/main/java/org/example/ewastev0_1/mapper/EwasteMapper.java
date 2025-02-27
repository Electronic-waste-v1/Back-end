package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EwasteMapper {

    Ewaste toEntity(EwasteRequest request);
    EwasteResponse toResponse(Ewaste entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Ewaste ewaste, EwasteRequest ewasteRequest);

}

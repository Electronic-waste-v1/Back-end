package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EwasteMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "annonce", ignore = true)
    Ewaste toEntity(EwasteRequest request);

    EwasteResponse toResponse(Ewaste entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Ewaste ewaste, EwasteRequest ewasteRequest);
}

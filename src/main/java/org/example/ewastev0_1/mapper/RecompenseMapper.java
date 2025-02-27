package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.Recompense;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.request.RecompenseRequest;
import org.example.ewastev0_1.dto.response.RecompenseResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RecompenseMapper {

    Recompense toEntity(RecompenseRequest request);
    RecompenseResponse toResponse(Recompense entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Recompense recompense, RecompenseRequest recompenseRequest);

}

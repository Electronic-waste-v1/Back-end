package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Recompense;
import org.example.ewastev0_1.dto.request.RecompenseRequest;
import org.example.ewastev0_1.dto.response.RecompenseResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecompenseMapper {

    Recompense toEntity(RecompenseRequest request);
    RecompenseResponse toResponse(Recompense entity);
}

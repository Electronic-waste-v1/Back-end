package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EwasteMapper {

    Ewaste toEntity(EwasteRequest request);
    EwasteResponse toResponse(Ewaste entity);
}

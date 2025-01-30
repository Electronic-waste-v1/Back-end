package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.RecyclingCenter;
import org.example.ewastev0_1.dto.request.RecyclingCenterRequest;
import org.example.ewastev0_1.dto.response.RecyclingcenterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecyclingMapper {

    RecyclingCenter toEntity(RecyclingCenterRequest recyclingCenterRequest);
    RecyclingcenterResponse toResponse(RecyclingCenter recyclingCenter);

}

package org.example.ewastev0_1.mapper;



import org.example.ewastev0_1.domain.entites.RecyclingCenter;
import org.example.ewastev0_1.dto.request.RecyclingCenterRequest;
import org.example.ewastev0_1.dto.response.RecyclingcenterResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RecyclingCenterMapper {

    RecyclingCenter toEntity(RecyclingCenterRequest recyclingCenterRequest);
    RecyclingcenterResponse toResponse(RecyclingCenter recyclingCenter);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget RecyclingCenter recyclingCenter, RecyclingCenterRequest recyclingCenterRequest);

}

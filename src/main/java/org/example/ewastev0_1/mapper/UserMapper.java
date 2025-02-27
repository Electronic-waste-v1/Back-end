package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.request.UserRequest;
import org.example.ewastev0_1.dto.response.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest userResquest);

    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User user, UserRequest userRequest);

}

package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.dto.request.UserRequest;
import org.example.ewastev0_1.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest userResquest);

    UserResponse toResponse(User user);

}

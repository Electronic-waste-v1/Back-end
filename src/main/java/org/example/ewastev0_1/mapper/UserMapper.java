package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.ActionHistorique;
import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.domain.entites.UserPoints;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.request.UserRequest;
import org.example.ewastev0_1.dto.response.ActionHistoryResponse;
import org.example.ewastev0_1.dto.response.UserPointsResponse;
import org.example.ewastev0_1.dto.response.UserResponse;
import org.example.ewastev0_1.dto.response.UserSummaryResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest userResquest);

    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User user, UserRequest userRequest);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "pointsAvailable", expression = "java(userPoints.getPointsTotal() - userPoints.getPointsUtilises())")
    UserPointsResponse toUserPointsResponse(UserPoints userPoints);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "pointsGained", source = "pointsGagnes")
    @Mapping(target = "actionDate", source = "dateAction")
    ActionHistoryResponse toActionHistoryResponse(ActionHistorique actionHistorique);
}

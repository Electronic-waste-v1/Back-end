package org.example.ewastev0_1.mapper;

import org.example.ewastev0_1.domain.entites.Notification;
import org.example.ewastev0_1.dto.response.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationResponse toResponse(Notification notification);
}
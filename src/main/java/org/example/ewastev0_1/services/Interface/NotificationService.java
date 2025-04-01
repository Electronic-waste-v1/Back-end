package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.domain.entites.Notification;
import org.example.ewastev0_1.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    NotificationResponse createNotification(Integer userId, String message);
    List<NotificationResponse> getNotificationsByUserId(Integer userId);
    List<NotificationResponse> getUnreadNotificationsByUserId(Integer userId);
    void markNotificationAsRead(Integer notificationId);
}
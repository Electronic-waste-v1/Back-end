package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.Notification;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.dto.response.NotificationResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.NotificationMapper;
import org.example.ewastev0_1.repository.NotificationRepository;
import org.example.ewastev0_1.repository.UserRepository;
import org.example.ewastev0_1.services.Interface.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationResponse createNotification(Integer userId, String message) {
        log.info("Creating notification for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setDateEnvoi(LocalDate.now());
        notification.setEstLu(false);

        Notification savedNotification = notificationRepository.save(notification);
        log.info("Notification created successfully with ID: {}", savedNotification.getId());

        return notificationMapper.toResponse(savedNotification);
    }

    @Override
    public List<NotificationResponse> getNotificationsByUserId(Integer userId) {
        log.info("Fetching all notifications for user ID: {}", userId);

        List<Notification> notifications = notificationRepository.findByUserId(userId);
        log.info("Retrieved {} notifications for user ID: {}", notifications.size(), userId);

        return notifications.stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponse> getUnreadNotificationsByUserId(Integer userId) {
        log.info("Fetching unread notifications for user ID: {}", userId);

        List<Notification> notifications = notificationRepository.findByUserIdAndEstLu(userId, false);
        log.info("Retrieved {} unread notifications for user ID: {}", notifications.size(), userId);

        return notifications.stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void markNotificationAsRead(Integer notificationId) {
        log.info("Marking notification as read with ID: {}", notificationId);

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + notificationId));

        notification.setEstLu(true);
        notificationRepository.save(notification);

        log.info("Notification marked as read with ID: {}", notificationId);
    }
}
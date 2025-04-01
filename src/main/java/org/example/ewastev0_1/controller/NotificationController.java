package org.example.ewastev0_1.controller;

import lombok.RequiredArgsConstructor;
import org.example.ewastev0_1.dto.response.NotificationResponse;
import org.example.ewastev0_1.services.Interface.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @RequestParam Integer userId,
            @RequestParam String message) {
        NotificationResponse response = notificationService.createNotification(userId, message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByUserId(@PathVariable Integer userId) {
        List<NotificationResponse> responses = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotificationsByUserId(@PathVariable Integer userId) {
        List<NotificationResponse> responses = notificationService.getUnreadNotificationsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{notificationId}/mark-as-read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Integer notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
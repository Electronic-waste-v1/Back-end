package org.example.ewastev0_1.repository;

import org.example.ewastev0_1.domain.entites.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.example.ewastev0_1.domain.entites.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserId(Integer userId);
    List<Notification> findByUserIdAndEstLu(Integer userId, Boolean estLu);
}
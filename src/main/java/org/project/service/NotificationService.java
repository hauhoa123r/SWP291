package org.project.service;

import org.project.entity.NotificationEntity;
import org.project.entity.UserEntity;

import java.util.List;

public interface NotificationService {
    List<NotificationEntity> getAllByUserId(Long userId);
    NotificationEntity send(UserEntity user, String title, String content);
    void sendNotification(Long userId, String title, String content);
    int sendBirthdayNotifications();
    void sendAppointmentNotification(Long appointmentId);
    void sendAppointmentChangeNotification(Long appointmentId);
    void sendAppointmentCancelNotification(Long appointmentId);
}

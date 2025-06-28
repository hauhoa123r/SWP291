package org.project.controlleradvice;

import jakarta.servlet.http.HttpSession;
import org.project.model.response.NotificationResponse;
import org.project.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModel {

    private NotificationService notificationService;

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ModelAttribute("notifications")
    public List<NotificationResponse> getNotifications(HttpSession session) {
        // TODO: replace by real userId
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return List.of();
        }
        return notificationService.getAllByUserId(userId);
    }
}

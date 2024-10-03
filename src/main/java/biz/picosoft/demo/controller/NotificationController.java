package biz.picosoft.demo.controller;

import biz.picosoft.demo.domain.Notification;
import biz.picosoft.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")

public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @PutMapping("/mark-as-seen/{username}")
    public ResponseEntity<Void> markNotificationsAsSeen(@PathVariable String username) {
        notificationService.markNotificationsAsSeen(username);
        return ResponseEntity.ok().build();
    }
}

package biz.picosoft.demo.controller;

import biz.picosoft.demo.client.kernel.model.global.CurrentUser;

import biz.picosoft.demo.domain.Notification;
import biz.picosoft.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;
import java.util.List;

@Controller
public class GreetingController {
    @Autowired
    private NotificationService notificationService;
@Autowired
private CurrentUser currentUser;
    @MessageMapping("/send")
    @SendTo("/topic/notifications")
    public Notification sendNotification(Notification notification) {
        notification.setTimestamp(LocalDate.now());
        return notificationService.saveNotification(notification);
    }

    @GetMapping("/notifications/unread-count/{username}")
    public ResponseEntity<Long> getUnreadNotificationCount(@PathVariable String username) {
        long unreadCount = notificationService.countUnreadNotificationsByUsername(username);
        return ResponseEntity.ok(unreadCount);
    }


}

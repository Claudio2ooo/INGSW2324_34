package it.unina.dietideals24.controller;

import it.unina.dietideals24.model.Notification;
import it.unina.dietideals24.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    @Qualifier("mainNotificationService")
    private INotificationService notificationService;

    @GetMapping("/user")
    public List<Notification> getNotificationsByReceiverId(@RequestParam Long receiverId){
        return notificationService.getNotificationsByReceiverId(receiverId);
    }

    @DeleteMapping(path = "{notificationId}")
    public void deleteNotificatiion(@PathVariable("notificationId") Long id){
        notificationService.deleteNotification(id);
    }
}

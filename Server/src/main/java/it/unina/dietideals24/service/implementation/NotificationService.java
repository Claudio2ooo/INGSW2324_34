package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.model.Notification;
import it.unina.dietideals24.repository.INotificationRepository;
import it.unina.dietideals24.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("mainNotificationService")
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotificationsByReceiverId(Long userId) {
        return notificationRepository.findByReceiverId(userId);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}

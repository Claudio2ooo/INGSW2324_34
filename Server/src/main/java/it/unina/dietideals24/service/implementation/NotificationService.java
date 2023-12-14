package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.repository.INotificationRepository;
import it.unina.dietideals24.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mainNotificationService")
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepository notificationRepository;
}

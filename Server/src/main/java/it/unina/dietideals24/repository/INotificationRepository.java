package it.unina.dietideals24.repository;

import it.unina.dietideals24.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverId(Long receiverId);
}

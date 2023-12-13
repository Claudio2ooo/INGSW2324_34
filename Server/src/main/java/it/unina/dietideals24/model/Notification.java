package it.unina.dietideals24.model;
import it.unina.dietideals24.compositePrimaryKey.NotificationId;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@IdClass(NotificationId.class)
public class Notification {
    private String state;

    @Id
    @ManyToOne
    private DietiUser receiver;

    @Id
    @ManyToOne
    private Auction endedAuction;
}

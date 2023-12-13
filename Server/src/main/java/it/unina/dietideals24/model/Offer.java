package it.unina.dietideals24.model;

import it.unina.dietideals24.compositePrimaryKey.OfferId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Entity
@IdClass(OfferId.class)
public class Offer {
    private Currency amount;
    private LocalDateTime timestamp;

    @Id
    @ManyToOne
    private DietiUser offerer;

    @Id
    @ManyToOne
    private Auction targetAuction;
}

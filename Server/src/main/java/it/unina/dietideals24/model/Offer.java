package it.unina.dietideals24.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Entity
public class Offer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private Currency amount;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "offerer_id", referencedColumnName = "id")
    private DietiUser offerer;

    @ManyToOne
    @JoinColumn(name = "english_auction_id", referencedColumnName = "id")
    private EnglishAuction targetEnglishAuction;

    @ManyToOne
    @JoinColumn(name = "reverse_auction_id", referencedColumnName = "id")
    private ReverseAuction targetReverseAuction;
}

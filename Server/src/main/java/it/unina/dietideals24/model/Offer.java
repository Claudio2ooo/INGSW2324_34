package it.unina.dietideals24.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offerer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_DIETI_USER"))
    private DietiUser offerer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "english_auction_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ENGLISH_AUCTION"))
    private EnglishAuction targetEnglishAuction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reverse_auction_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_REVERSE_AUCTION"))
    private ReverseAuction targetReverseAuction;
}

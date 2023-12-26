package it.unina.dietideals24.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private BigDecimal amount;
    private LocalDateTime timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offerer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_DIETI_USER"))
    private DietiUser offerer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "english_auction_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ENGLISH_AUCTION"))
    private EnglishAuction targetEnglishAuction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "downward_auction_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_DOWNWARD_AUCTION"))
    private DownwardAuction targetDownwardAuction;

    public Offer(BigDecimal amount, DietiUser offerer, EnglishAuction targetEnglishAuction){
        this.amount = amount;
        this.offerer = offerer;
        this.targetEnglishAuction = targetEnglishAuction;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
    }

    public Offer(BigDecimal amount, DietiUser offerer, DownwardAuction targetDownwardAuction) {
        this.amount = amount;
        this.offerer = offerer;
        this.targetDownwardAuction = targetDownwardAuction;
        this.timestamp = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
    }
}

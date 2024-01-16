package it.unina.dietideals24.model;

import it.unina.dietideals24.enumeration.StateEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private StateEnum state;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_DIETI_USER"))
    private DietiUser receiver;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ended_english_auction_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ENGLISH_AUCTION"))
//    private EnglishAuction endedEnglishAuction;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ended_downward_auction_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_DOWNWARD_AUCTION"))
//    private DownwardAuction endedDownwardAuction;

    private String titleOfTheAuction;
    private String imageUrlOfTheAuction;
    private BigDecimal finalPrice;
    private boolean pushed;

    public Notification(Long id, StateEnum state, DietiUser receiver, String titleOfTheAuction, String imageUrlOfTheAuction, BigDecimal finalPrice, boolean pushed) {
        this.id = id;
        this.state = state;
        this.receiver = receiver;
        this.titleOfTheAuction = titleOfTheAuction;
        this.imageUrlOfTheAuction = imageUrlOfTheAuction;
        this.finalPrice = finalPrice;
        this.pushed = pushed;
    }

    public Notification(StateEnum state, DietiUser receiver, String titleOfTheAuction, String imageUrlOfTheAuction, BigDecimal finalPrice) {
        this.state = state;
        this.receiver = receiver;
        this.titleOfTheAuction = titleOfTheAuction;
        this.imageUrlOfTheAuction = imageUrlOfTheAuction;
        this.finalPrice = finalPrice;
        this.pushed = false;
    }

    public Notification(){

    }
}

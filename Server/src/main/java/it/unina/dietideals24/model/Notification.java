package it.unina.dietideals24.model;

import it.unina.dietideals24.enumeration.StateEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private StateEnum state;

    @ManyToOne(cascade = CascadeType.ALL)
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
}

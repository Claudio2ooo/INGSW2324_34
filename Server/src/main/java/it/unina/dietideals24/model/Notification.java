package it.unina.dietideals24.model;

import it.unina.dietideals24.enumeration.StateEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

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
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private DietiUser receiver;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ended_english_auction_id", referencedColumnName = "id")
    private EnglishAuction endedEnglishAuction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ended_reverse_auction_id", referencedColumnName = "id")
    private ReverseAuction endedReverseAuction;
}

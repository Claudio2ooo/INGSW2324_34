package it.unina.dietideals24.model;

import it.unina.dietideals24.enumeration.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalTime;
import java.util.Currency;


@Data
@Entity
public class Auction {

    @Id
    @SequenceGenerator(
            name = "auction_sequence",
            sequenceName = "auction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "auction_sequence"
    )
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String imageURL;
    @Transient
    private Image image;
    private Currency startingPrice;
    private LocalTime timer;
    @ManyToOne
    private DietiUser owner;
}


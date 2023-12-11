package it.unina.dietideals24.model;

import it.unina.dietideals24.enumeration.Category;
import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalTime;
import java.util.Currency;

@Entity
@Table
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
    private Category category;
    private Image image;
    private Currency startingPrice;
    private LocalTime timer;
    private DietiUser owner;
}


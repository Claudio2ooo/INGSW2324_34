package it.unina.dietideals24.model;

import it.unina.dietideals24.enumeration.CategoryEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.util.Currency;


@Data
@MappedSuperclass
public class Auction {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    private String imageURL;
    @Transient
    private Image image;
    private Currency startingPrice;
    private Currency currentPrice;
    private Long timer;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private DietiUser owner;
}


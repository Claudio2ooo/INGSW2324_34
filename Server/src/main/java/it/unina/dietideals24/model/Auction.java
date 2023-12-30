package it.unina.dietideals24.model;

import it.unina.dietideals24.enumeration.CategoryEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    @Column(name = "timer")
    private Long timerInMilliseconds;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_DIETI_USER"))
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private DietiUser owner;
}


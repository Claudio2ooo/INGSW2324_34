package it.unina.dietideals24.model;

import it.unina.dietideals24.enumeration.CategoryEnum;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class EnglishAuction extends Auction {
    private BigDecimal increaseAmount;

}

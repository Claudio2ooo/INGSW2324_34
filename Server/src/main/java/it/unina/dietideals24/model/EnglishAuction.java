package it.unina.dietideals24.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class EnglishAuction extends Auction {
    private BigDecimal increaseAmount;
}

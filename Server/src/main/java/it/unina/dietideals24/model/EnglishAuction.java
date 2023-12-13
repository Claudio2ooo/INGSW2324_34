package it.unina.dietideals24.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Currency;

@Entity
public class EnglishAuction extends Auction {
    private Currency increaseAmount;
}

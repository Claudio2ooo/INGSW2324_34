package it.unina.dietideals24.model;

import jakarta.persistence.Entity;

import java.util.Currency;

@Entity
public class ReverseAuction extends Auction{
    private Currency decreaseAmount;
    private Currency minimumPrice;

}

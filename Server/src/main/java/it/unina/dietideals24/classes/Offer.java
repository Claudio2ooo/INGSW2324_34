package it.unina.dietideals24.classes;

import java.time.LocalDateTime;
import java.util.Currency;

public class Offer {
    private Currency amount;
    private LocalDateTime timestamp;
    private DietiUser offerer;
    private Auction targetAuction;
}

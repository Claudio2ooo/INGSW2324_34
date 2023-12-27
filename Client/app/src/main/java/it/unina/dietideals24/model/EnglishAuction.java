package it.unina.dietideals24.model;

import java.math.BigDecimal;

import it.unina.dietideals24.enumerations.CategoryEnum;

public class EnglishAuction extends Auction {
    private BigDecimal increaseAmount;

    public EnglishAuction(String title, String description, CategoryEnum category, String imageURL, BigDecimal startingPrice, BigDecimal currentPrice, Long timerInMilliseconds, BigDecimal increaseAmount) {
        super(title, description, category, imageURL, startingPrice, currentPrice, timerInMilliseconds);
        this.increaseAmount = increaseAmount;
    }

    public BigDecimal getIncreaseAmount() {
        return increaseAmount;
    }

    public void setIncreaseAmount(BigDecimal increaseAmount) {
        this.increaseAmount = increaseAmount;
    }
}

package it.unina.dietideals24.dto;

import java.math.BigDecimal;

import it.unina.dietideals24.enumerations.CategoryEnum;

public class EnglishAuctionDto {
    private String title;
    private String description;
    private CategoryEnum category;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private Long timerInMilliseconds;
    private BigDecimal increaseAmount;
    private Long ownerId;

    public EnglishAuctionDto(String title, String description, CategoryEnum category, BigDecimal startingPrice, BigDecimal currentPrice, Long timerInMilliseconds, BigDecimal increaseAmount, Long ownerId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.timerInMilliseconds = timerInMilliseconds;
        this.increaseAmount = increaseAmount;
        this.ownerId = ownerId;
    }
}


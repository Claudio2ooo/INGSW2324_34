package it.unina.dietideals24.model;

import java.math.BigDecimal;

import it.unina.dietideals24.enumerations.CategoryEnum;

public class Auction {
    private Long id;
    private String title;
    private String description;
    private CategoryEnum category;
    private String imageURL;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private Long timerInMilliseconds;

    private DietiUser owner;

    public Auction(String title, String description, CategoryEnum category, String imageURL, BigDecimal startingPrice, BigDecimal currentPrice, Long timerInMilliseconds) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageURL = imageURL;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.timerInMilliseconds = timerInMilliseconds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getTimerInMilliseconds() {
        return timerInMilliseconds;
    }

    public void setTimerInMilliseconds(Long timerInMilliseconds) {
        this.timerInMilliseconds = timerInMilliseconds;
    }

    public DietiUser getOwner() {
        return owner;
    }

    public void setOwner(DietiUser owner) {
        this.owner = owner;
    }
}

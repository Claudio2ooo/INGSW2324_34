package it.unina.dietideals24.api.model;

import it.unina.dietideals24.enumeration.Category;

import java.awt.*;
import java.time.LocalTime;
import java.util.Currency;

public abstract class Auction {
    private String title;
    private String description;
    private Category category;
    private Image image;
    private Currency startingPrice;
    private LocalTime timer;
    private DietiUser owner;
}


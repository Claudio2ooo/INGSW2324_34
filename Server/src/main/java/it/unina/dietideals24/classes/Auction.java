package it.unina.dietideals24.classes;

import it.unina.dietideals24.enumerations.Category;

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


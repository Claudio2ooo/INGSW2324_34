package classes;

import enumerations.Categoria_ENUM;

import java.awt.*;
import java.time.LocalTime;
import java.util.Currency;

public abstract class Asta {
    private String titolo;
    private String descrizione;
    private Categoria_ENUM categoria;
    private Image immagine;
    private Currency prezzoIniziale;
    private LocalTime timer;
    private Utente proprietario;
}


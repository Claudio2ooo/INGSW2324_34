package it.unina.dietideals24.compositePrimaryKey;

import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DietiUser;

import java.io.Serializable;

public class NotificationId implements Serializable {
    private DietiUser receiver;
    private Auction endedAuction;
}

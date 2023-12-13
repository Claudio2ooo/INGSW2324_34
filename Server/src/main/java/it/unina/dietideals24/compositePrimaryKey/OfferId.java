package it.unina.dietideals24.compositePrimaryKey;

import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DietiUser;

import java.io.Serializable;

public class OfferId implements Serializable {
    private DietiUser offerer;

    private Auction targetAuction;

    public OfferId(DietiUser offerer, Auction targetAuction){
        this.offerer = offerer;
        this.targetAuction = targetAuction;
    }
}

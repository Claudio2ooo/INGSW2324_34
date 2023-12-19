package it.unina.dietideals24.auction_timer;

import it.unina.dietideals24.model.Auction;
import lombok.Getter;

import java.util.TimerTask;

@Getter
public abstract class AuctionTask extends TimerTask {
    private final Auction auction;

    protected AuctionTask(Auction auction){
        super();
        this.auction = auction;
    }

}

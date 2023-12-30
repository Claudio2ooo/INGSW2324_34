package it.unina.dietideals24.retrofit.api;

import java.util.ArrayList;

import it.unina.dietideals24.model.Auction;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DownwardAuctionAPI {
    @GET("/downward-auctions")
    Call<ArrayList<Auction>> getDownwardAuctions();
}

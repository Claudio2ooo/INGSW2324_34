package it.unina.dietideals24.retrofit.api;

import java.util.ArrayList;

import it.unina.dietideals24.dto.EnglishAuctionDto;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.EnglishAuction;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EnglishAuctionAPI {
    @GET("/english-auctions")
    Call<ArrayList<Auction>> getEnglishAuctions();

    @POST("/english-auctions/create")
    Call<EnglishAuction> createAuction(@Body EnglishAuctionDto englishAuctionDto);
}

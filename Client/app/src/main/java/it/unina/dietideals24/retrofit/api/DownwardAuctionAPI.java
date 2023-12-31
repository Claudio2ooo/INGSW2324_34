package it.unina.dietideals24.retrofit.api;

import android.media.tv.AitInfo;

import java.util.ArrayList;

import it.unina.dietideals24.dto.DownwardAuctionDto;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DownwardAuctionAPI {
    @GET("/downward-auctions")
    Call<ArrayList<DownwardAuction>> getDownwardAuctions();

    @GET("/downward-auctions/{id}")
    Call<DownwardAuction> getById(@Path("id") Long idAuction);

    @POST("/downward-auctions/create")
    Call<DownwardAuction> createAuction(@Body DownwardAuctionDto downwardAuctionDto);

}

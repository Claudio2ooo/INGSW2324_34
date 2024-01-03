package it.unina.dietideals24.retrofit.api;

import java.util.ArrayList;

import it.unina.dietideals24.model.Offer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OfferAPI {
    @GET("/offers/english/{id}")
    Call<ArrayList<Offer>> getOffersByEnglishAuctionId(@Path("id") Long englishAuctionId);

    @GET("/offers/downward/{id}")
    Call<ArrayList<Offer>> getOffersByDownwardAuctionId(@Path("id") Long downwardAuctionId);
}

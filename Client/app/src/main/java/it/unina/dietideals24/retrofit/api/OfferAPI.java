package it.unina.dietideals24.retrofit.api;

import java.math.BigDecimal;
import java.util.ArrayList;

import it.unina.dietideals24.model.Offer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OfferAPI {
    @GET("/offers/english/{id}")
    Call<ArrayList<Offer>> getOffersByEnglishAuctionId(@Path("id") Long englishAuctionId);

    @GET("/offers/downward/{id}")
    Call<ArrayList<Offer>> getOffersByDownwardAuctionId(@Path("id") Long downwardAuctionId);

    @POST("/offers/english/{id}")
    Call<String> makeEnglishOffer(@Path("id") Long id, @Body BigDecimal bigDecimal);

    @POST("/offers/downward/{id}")
    Call<String> makeDownwardOffer(@Path("id") Long id);
}

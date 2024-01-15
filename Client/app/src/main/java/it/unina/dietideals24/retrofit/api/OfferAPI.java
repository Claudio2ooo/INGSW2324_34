package it.unina.dietideals24.retrofit.api;

import java.util.ArrayList;
import java.util.Set;

import it.unina.dietideals24.dto.OfferDto;
import it.unina.dietideals24.model.EnglishAuction;
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

    @GET("/offers/english/offerer/{id}")
    Call<ArrayList<EnglishAuction>> getAuctionsByOffererId(@Path("id") Long id);

    @POST("/offers/english")
    Call<String> makeEnglishOffer(@Body OfferDto offerDto);

    @POST("/offers/downward")
    Call<String> makeDownwardOffer(@Body OfferDto offerDto);

}

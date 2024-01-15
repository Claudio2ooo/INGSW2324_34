package it.unina.dietideals24.retrofit.api;

import java.util.ArrayList;

import it.unina.dietideals24.dto.DownwardAuctionDto;
import it.unina.dietideals24.enumerations.CategoryEnum;
import it.unina.dietideals24.model.DownwardAuction;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DownwardAuctionAPI {
    @GET("/downward-auctions")
    Call<ArrayList<DownwardAuction>> getDownwardAuctions();

    @GET("/downward-auctions/category/{category}")
    Call<ArrayList<DownwardAuction>> getByCategory(@Path("category") CategoryEnum category);

    @GET("/downward-auctions/{id}")
    Call<DownwardAuction> getById(@Path("id") Long idAuction);

    @GET("/downward-auctions/search/{keyword}")
    Call<ArrayList<DownwardAuction>> getByKeyword(@Path("keyword") String keyword);

    @GET("/downward-auctions/owner/{id}")
    Call<ArrayList<DownwardAuction>> getDownwardAuctionsByOwnerId(@Path("id") Long id);

    @POST("/downward-auctions/create")
    Call<DownwardAuction> createAuction(@Body DownwardAuctionDto downwardAuctionDto);


}

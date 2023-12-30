package it.unina.dietideals24.retrofit.api;

import java.util.ArrayList;

import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DietiUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DietiUserAPI {

    @GET("/users/email/{email}")
    Call<DietiUser> getUserByEmail(@Path("email") String email);

}

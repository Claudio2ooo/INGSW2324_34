package it.unina.dietideals24.retrofit;

import java.util.List;

import it.unina.dietideals24.dto.LoginDto;
import it.unina.dietideals24.dto.RegisterDto;
import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DietiDealsService {

    @POST
    public Call<List<DietiUser>> getUsers();

    @POST("/auth/register")
    public Call<DietiUser> register(@Body RegisterDto registerDto);

    @POST("/auth/login")
    public Call<LoginResponse> login(@Body LoginDto loginDto);

}

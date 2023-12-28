package it.unina.dietideals24.retrofit;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit = null;
    private static OkHttpClient httpClient = null;
    private static final String BASE_URL = "http://10.0.2.2:9090/";

    private RetrofitService() {}

    public static Retrofit getRetrofitInstance(String token) {
        if (token != null) {
            httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request newRequest = chain.request().newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }).build();
        } else {
            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build();
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .client(httpClient)
                    .build();
        }

        return retrofit;
    }

}

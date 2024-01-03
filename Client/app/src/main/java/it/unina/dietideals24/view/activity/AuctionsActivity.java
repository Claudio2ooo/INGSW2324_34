package it.unina.dietideals24.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.EndElementListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.adapter.AuctionAdapter;
import it.unina.dietideals24.enumerations.CategoryEnum;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DownwardAuctionAPI;
import it.unina.dietideals24.retrofit.api.EnglishAuctionAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionsActivity extends AppCompatActivity {

    private ImageView backBtn;
    private TextView titleText;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private static final int HORIZONTAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auctions);

        initializeViews();

        Intent intent = getIntent();
        String typeOfAuction = intent.getExtras().getString("typeOfAuction");
        String category = intent.getExtras().getString("category");

        if (category.equals("none")){
            if (typeOfAuction.equals("English"))
                initializeEnglishAuctions();
            else
                initializeDownwardAuctions();
        } else {
            if (typeOfAuction.equals("English"))
                initializeEnglishAuctionsByCategory(CategoryEnum.valueOf(category.toUpperCase()));
            else
                initializeDownwardAuctionsByCategory(CategoryEnum.valueOf(category.toUpperCase()));
        }

        backBtn.setOnClickListener(v -> finish());
    }

    private void initializeDownwardAuctionsByCategory(CategoryEnum category){
        DownwardAuctionAPI downwardAuctionAPI = RetrofitService.getRetrofitInstance().create(DownwardAuctionAPI.class);
        downwardAuctionAPI.getByCategory(category).enqueue(new Callback<ArrayList<DownwardAuction>>() {
            @Override
            public void onResponse(Call<ArrayList<DownwardAuction>> call, Response<ArrayList<DownwardAuction>> response) {
                ArrayList<Auction> auctions;

                if (response.body() == null)
                    auctions= new ArrayList<>();
                else
                    auctions = new ArrayList<>(response.body());

                initializeAuctionAdapter(auctions);
            }

            @Override
            public void onFailure(Call<ArrayList<DownwardAuction>> call, Throwable t) {
                initializeAuctionAdapter(new ArrayList<>());
            }
        });

        progressBar.setVisibility(View.GONE);
        String title = "Aste al ribasso: "+category.name().toLowerCase();
        titleText.setText(title);
    }

    private void initializeEnglishAuctionsByCategory(CategoryEnum category){
        EnglishAuctionAPI englishAuctionAPI = RetrofitService.getRetrofitInstance().create(EnglishAuctionAPI.class);
        englishAuctionAPI.getByCategory(category).enqueue(new Callback<ArrayList<EnglishAuction>>() {
            @Override
            public void onResponse(Call<ArrayList<EnglishAuction>> call, Response<ArrayList<EnglishAuction>> response) {
                ArrayList<Auction> auctions;

                if (response.body() == null)
                    auctions= new ArrayList<>();
                else
                    auctions = new ArrayList<>(response.body());

                initializeAuctionAdapter(auctions);
            }

            @Override
            public void onFailure(Call<ArrayList<EnglishAuction>> call, Throwable t) {
                initializeAuctionAdapter(new ArrayList<>());
            }
        });

        progressBar.setVisibility(View.GONE);
        String title = "Aste all'inglese: "+category.name().toLowerCase();
        titleText.setText(title);
    }

    private void initializeDownwardAuctions() {
        DownwardAuctionAPI downwardAuctionAPI = RetrofitService.getRetrofitInstance().create(DownwardAuctionAPI.class);
        downwardAuctionAPI.getDownwardAuctions().enqueue(new Callback<ArrayList<DownwardAuction>>() {
            @Override
            public void onResponse(Call<ArrayList<DownwardAuction>> call, Response<ArrayList<DownwardAuction>> response) {
                ArrayList<Auction> auctions;

                if (response.body() == null)
                    auctions= new ArrayList<>();
                else
                    auctions = new ArrayList<>(response.body());

                initializeAuctionAdapter(auctions);
            }

            @Override
            public void onFailure(Call<ArrayList<DownwardAuction>> call, Throwable t) {
                initializeAuctionAdapter(new ArrayList<>());
            }
        });

        progressBar.setVisibility(View.GONE);
        String title = "Aste al ribasso";
        titleText.setText(title);
    }


    private void initializeEnglishAuctions() {
        EnglishAuctionAPI englishAuctionAPI = RetrofitService.getRetrofitInstance().create(EnglishAuctionAPI.class);
        englishAuctionAPI.getEnglishAuctions().enqueue(new Callback<ArrayList<EnglishAuction>>() {
            @Override
            public void onResponse(Call<ArrayList<EnglishAuction>> call, Response<ArrayList<EnglishAuction>> response) {
                ArrayList<Auction> auctions;

                if (response.body() == null)
                    auctions= new ArrayList<>();
                else
                    auctions = new ArrayList<>(response.body());

                initializeAuctionAdapter(auctions);
            }

            @Override
            public void onFailure(Call<ArrayList<EnglishAuction>> call, Throwable t) {
                initializeAuctionAdapter(new ArrayList<>());
            }
        });

        progressBar.setVisibility(View.GONE);
        String title = "Aste all'inglese";
        titleText.setText(title);
    }

    private void initializeAuctionAdapter(ArrayList<Auction> auctions) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> adapterAuction = new AuctionAdapter(auctions, HORIZONTAL);
        recyclerView.setAdapter(adapterAuction);
    }

    private void initializeViews() {
        backBtn = findViewById(R.id.backBtn);
        titleText = findViewById(R.id.titleTextView);

        recyclerView = findViewById(R.id.auctionsList);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }
}
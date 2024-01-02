package it.unina.dietideals24.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.adapter.AuctionAdapter;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.EnglishAuctionAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnglishAuctionsActivity extends AppCompatActivity {

    private ImageView backBtn;
    private ProgressBar englishAuctionProgressBar;
    private RecyclerView recyclerViewEnglishAuction;
    private static final int HORIZONTAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_auctions);

        initializeViews();
        initializeEnglishAuctions();

        backBtn.setOnClickListener(v -> finish());
    }

    private void initializeEnglishAuctions() {
        EnglishAuctionAPI englishAuctionAPI = RetrofitService.getRetrofitInstance().create(EnglishAuctionAPI.class);
        englishAuctionAPI.getEnglishAuctions().enqueue(new Callback<ArrayList<EnglishAuction>>() {
            @Override
            public void onResponse(Call<ArrayList<EnglishAuction>> call, Response<ArrayList<EnglishAuction>> response) {
                ArrayList<Auction> auctions = new ArrayList<>(response.body());

                englishAuctionProgressBar.setVisibility(View.GONE);
                initializeAuctionAdapter(auctions);
            }

            @Override
            public void onFailure(Call<ArrayList<EnglishAuction>> call, Throwable t) {
                englishAuctionProgressBar.setVisibility(View.GONE);
                initializeAuctionAdapter(null);
            }
        });
    }

    private void initializeAuctionAdapter(ArrayList<Auction> auctions) {
        recyclerViewEnglishAuction.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> adapterAuction = new AuctionAdapter(auctions, HORIZONTAL);
        recyclerViewEnglishAuction.setAdapter(adapterAuction);
    }

    private void initializeViews() {
        backBtn = findViewById(R.id.backBtn);
        recyclerViewEnglishAuction = findViewById(R.id.englishAuctionsList);

        englishAuctionProgressBar = findViewById(R.id.englishAuctionProgressBar);
        englishAuctionProgressBar.setVisibility(View.VISIBLE);
    }
}
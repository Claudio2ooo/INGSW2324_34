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
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DownwardAuctionAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownwardAuctionsActivity extends AppCompatActivity {

    private ImageView backBtn;
    private ProgressBar downwardAuctionProgressBar;
    private RecyclerView recyclerViewDownwardAuction;
    private static final int HORIZONTAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downward_auctions);

        initializeViews();
        initializeDownwardAuction();

        backBtn.setOnClickListener(v -> finish());
    }

    private void initializeDownwardAuction() {
        DownwardAuctionAPI downwardAuctionAPI = RetrofitService.getRetrofitInstance().create(DownwardAuctionAPI.class);
        downwardAuctionAPI.getDownwardAuctions().enqueue(new Callback<ArrayList<DownwardAuction>>() {
            @Override
            public void onResponse(Call<ArrayList<DownwardAuction>> call, Response<ArrayList<DownwardAuction>> response) {
                ArrayList<Auction> auctions = new ArrayList<>(response.body());

                downwardAuctionProgressBar.setVisibility(View.GONE);
                initializeAuctionAdapter(auctions);
            }

            @Override
            public void onFailure(Call<ArrayList<DownwardAuction>> call, Throwable t) {
                downwardAuctionProgressBar.setVisibility(View.GONE);
                initializeAuctionAdapter(null);
            }
        });
    }

    private void initializeAuctionAdapter(ArrayList<Auction> auctions) {
        recyclerViewDownwardAuction.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> adapterAuction = new AuctionAdapter(auctions, HORIZONTAL);
        recyclerViewDownwardAuction.setAdapter(adapterAuction);
    }

    private void initializeViews() {
        backBtn = findViewById(R.id.backBtn);
        recyclerViewDownwardAuction = findViewById(R.id.downwardAuctionsList);

        downwardAuctionProgressBar = findViewById(R.id.downwardAuctionProgressBar);
        downwardAuctionProgressBar.setVisibility(View.VISIBLE);
    }
}
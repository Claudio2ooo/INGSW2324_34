package it.unina.dietideals24.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Timestamp;
import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.adapter.OfferAdapter;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.model.Offer;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DownwardAuctionAPI;
import it.unina.dietideals24.retrofit.api.EnglishAuctionAPI;
import it.unina.dietideals24.retrofit.api.OfferAPI;
import it.unina.dietideals24.utils.ConvertSecondsToHourMinuteSeconds;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionDetailsActivity extends AppCompatActivity {

    private ImageView backBtn;
    private ImageView image;
    private TextView title;
    private TextView categoryName;
    private TextView description;
    private TextView currentPrice;
    private TextView timer;
    private TextView sellerInfoText;
    private TextInputLayout offerTextLayout;
    private EditText offerEditText;
    private Button makeAnOfferBtn;
    private ConstraintLayout sellerInfoBtn;
    private RecyclerView recyclerViewOfferrers;

    private Auction auction;
    private ArrayList<Offer> offerrers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_details);

        initializeViews();

        Long idAuction = getIntent().getLongExtra("id", -1);
        if (getIntent().getStringExtra("type").equals("ENGLISH")) {
            getEnglishAuction(idAuction);
            getOfferrersEnglishAuction(idAuction);
        } else if (getIntent().getStringExtra("type").equals("DOWNWARD")) {
            getDownwardAuction(idAuction);
            getOfferrersDownwardAuction(idAuction);
        }

        backBtn.setOnClickListener(v -> finish());

        sellerInfoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SellerInfoActivity.class);
            intent.putExtra("id", auction.getOwner().getId());
            startActivity(intent);
        });
    }

    private void initializeFields(Auction auction) {
        title.setText(auction.getTitle());
        categoryName.setText(auction.getCategory().toString());
        description.setText(auction.getDescription());
        currentPrice.setText(String.format("€%s", auction.getCurrentPrice().toString()));
        startTimer();
        String sellerInfo = auction.getOwner().getName() + " " + auction.getOwner().getSurname();
        sellerInfoText.setText(sellerInfo);
        if (auction instanceof EnglishAuction)
            offerTextLayout.setHint(auction.getCurrentPrice().toString() + " + " + ((EnglishAuction) auction).getIncreaseAmount());
        else
            offerTextLayout.setHint(auction.getCurrentPrice().toString());
    }

    private void startTimer() {
        Timestamp creation = new Timestamp(auction.getCreatedAt().getTime());
        Timestamp deadline = new Timestamp(creation.getTime() + auction.getTimerInMilliseconds());
        new CountDownTimer(deadline.getTime() - System.currentTimeMillis(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(ConvertSecondsToHourMinuteSeconds.formatSeconds(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void getEnglishAuction(Long idAuction) {
        EnglishAuctionAPI englishAuctionAPI = RetrofitService.getRetrofitInstance().create(EnglishAuctionAPI.class);
        englishAuctionAPI.getById(idAuction).enqueue(new Callback<EnglishAuction>() {
            @Override
            public void onResponse(Call<EnglishAuction> call, Response<EnglishAuction> response) {
                if (response.code() == 200) {
                    auction = response.body();
                    assert auction != null;
                    initializeFields(auction);
                }
            }

            @Override
            public void onFailure(Call<EnglishAuction> call, Throwable t) {
                //TODO messaggio "l'asta non è più disponibile"
                finish();
            }
        });
    }

    private void getDownwardAuction(Long idAuction) {
        DownwardAuctionAPI downwardAuctionAPI = RetrofitService.getRetrofitInstance().create(DownwardAuctionAPI.class);
        downwardAuctionAPI.getById(idAuction).enqueue(new Callback<DownwardAuction>() {
            @Override
            public void onResponse(Call<DownwardAuction> call, Response<DownwardAuction> response) {
                if (response.code() == 200) {
                    auction = response.body();
                    assert auction != null;
                    initializeFields(auction);
                }
            }

            @Override
            public void onFailure(Call<DownwardAuction> call, Throwable t) {
                //TODO messaggio "l'asta non è più disponibile"
                finish();
            }
        });
    }

    private void getOfferrersEnglishAuction(Long idAuction) {
        OfferAPI offerAPI = RetrofitService.getRetrofitInstance().create(OfferAPI.class);
        offerAPI.getOffersByEnglishAuctionId(idAuction).enqueue(new Callback<ArrayList<Offer>>() {
            @Override
            public void onResponse(Call<ArrayList<Offer>> call, Response<ArrayList<Offer>> response) {
                if (response.code() == 200) {
                    offerrers = response.body();
                    initializeOfferrers();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Offer>> call, Throwable t) {

            }
        });
    }

    private void getOfferrersDownwardAuction(Long idAuction) {
        OfferAPI offerAPI = RetrofitService.getRetrofitInstance().create(OfferAPI.class);
        offerAPI.getOffersByDownwardAuctionId(idAuction).enqueue(new Callback<ArrayList<Offer>>() {
            @Override
            public void onResponse(Call<ArrayList<Offer>> call, Response<ArrayList<Offer>> response) {
                if (response.code() == 200) {
                    offerrers = response.body();
                    initializeOfferrers();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Offer>> call, Throwable t) {

            }
        });
    }

    private void initializeOfferrers() {
        recyclerViewOfferrers.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter<OfferAdapter.SellerViewHolder> sellerAdapter = new OfferAdapter(offerrers);
        recyclerViewOfferrers.setAdapter(sellerAdapter);
    }

    private void initializeViews() {
        image = findViewById(R.id.imageAcution);
        title = findViewById(R.id.titleAuction);
        categoryName = findViewById(R.id.categoryAuction);
        description = findViewById(R.id.descriptionText);
        currentPrice = findViewById(R.id.currentPriceAuction);
        timer = findViewById(R.id.timerAuction);
        offerEditText = findViewById(R.id.inputAnOffer);
        offerTextLayout = findViewById(R.id.offerTextLayout);
        makeAnOfferBtn = findViewById(R.id.makeAnOfferBtn);
        backBtn = findViewById(R.id.backBtn);
        sellerInfoBtn = findViewById(R.id.sellerInfo);
        sellerInfoText = findViewById(R.id.sellerInfoText);
        recyclerViewOfferrers = findViewById(R.id.offerrersList);
    }
}
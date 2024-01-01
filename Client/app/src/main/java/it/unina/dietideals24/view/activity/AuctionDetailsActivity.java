package it.unina.dietideals24.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;

import it.unina.dietideals24.R;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DownwardAuctionAPI;
import it.unina.dietideals24.retrofit.api.EnglishAuctionAPI;
import it.unina.dietideals24.utils.ConvertSecondsToHourMinuteSeconds;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionDetailsActivity extends AppCompatActivity {

    private ImageView backBtn, image;
    private TextView title, categoryName, description, currentPrice, timer, sellerInfoText;
    private TextInputLayout offerTextLayout;
    private EditText offerEditText;
    private Button makeAnOfferBtn;
    private ConstraintLayout sellerInfoBtn;

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    private Auction auction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_details);

        initializeViews();

        Long idAuction = getIntent().getLongExtra("id", -1);
        if (getIntent().getStringExtra("type").equals("ENGLISH"))
            getEnglishAuction(idAuction);
        else if (getIntent().getStringExtra("type").equals("DOWNWARD"))
            getDownwardAuction(idAuction);

        backBtn.setOnClickListener(v -> finish());

        sellerInfoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SellerInfoActivity.class);
            startActivity(intent);
        });
    }

    private void initializeFields(Auction auction) {
        title.setText(auction.getTitle());
        categoryName.setText(auction.getCategory().toString());
        description.setText(auction.getDescription());
        currentPrice.setText(String.format("€%s", auction.getCurrentPrice().toString()));
        timer.setText(String.format(ConvertSecondsToHourMinuteSeconds.formatSeconds(auction.getTimerInMilliseconds())));
        String sellerInfo = auction.getOwner().getName() + " " + auction.getOwner().getSurname();
        sellerInfoText.setText(sellerInfo);
        if (auction instanceof EnglishAuction)
            offerTextLayout.setHint(auction.getCurrentPrice().toString() + " + " + ((EnglishAuction) auction).getIncreaseAmount());
        else
            offerTextLayout.setHint(auction.getCurrentPrice().toString());
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
    }
}
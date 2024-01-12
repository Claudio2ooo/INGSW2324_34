package it.unina.dietideals24.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import it.unina.dietideals24.R;
import it.unina.dietideals24.adapter.OfferAdapter;
import it.unina.dietideals24.dto.OfferDto;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.model.Offer;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DownwardAuctionAPI;
import it.unina.dietideals24.retrofit.api.EnglishAuctionAPI;
import it.unina.dietideals24.retrofit.api.OfferAPI;
import it.unina.dietideals24.utils.ConvertSecondsToHourMinuteSeconds;
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;
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
    private TextView messageNoOfferrers;
    private TextInputLayout offerTextLayout;
    private EditText offerEditText;
    private Button makeAnOfferBtn;
    private ConstraintLayout sellerInfoBtn;
    private RecyclerView recyclerViewOfferrers;
    private ConstraintLayout offerersConstraintLayout;
    private Auction auction;
    private ArrayList<Offer> offerrers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_details);

        initializeViews();

        Long idAuction = getIntent().getLongExtra("id", -1);
        if (getIntent().getStringExtra("type").equals("ENGLISH")) {
            makeAnOfferBtn.setText(R.string.make_an_offer_label);
            getEnglishAuction(idAuction);
            getOfferrersEnglishAuction(idAuction);
        } else if (getIntent().getStringExtra("type").equals("DOWNWARD")) {
            makeAnOfferBtn.setText(R.string.buy_label);
            getDownwardAuction(idAuction);
            hideOfferersSection();
        }

        backBtn.setOnClickListener(v -> finish());

        sellerInfoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SellerInfoActivity.class);
            intent.putExtra("id", auction.getOwner().getId());
            startActivity(intent);
        });

        makeAnOfferBtn.setOnClickListener(v -> {
            if (auction instanceof EnglishAuction)
                showEnglishAuctionConfirmOfferDialog();
            else if (auction instanceof DownwardAuction)
                showDownwardAuctionConfirmOfferDialog();
        });
    }

    private void showFailedOfferDialog(String errorMessage){
        ConstraintLayout failedOfferConstraintLayout = findViewById(R.id.failedOfferConstraintLayout);
        View viewFailedOfferDialog = LayoutInflater.from(AuctionDetailsActivity.this).inflate(R.layout.failed_offer_dialog, failedOfferConstraintLayout);

        Button backToAuctionButton = findViewById(R.id.backToAuctionBtn);

        TextView errorText = findViewById(R.id.failedOfferText);
        errorText.setText(errorMessage);

        AlertDialog.Builder builder = new AlertDialog.Builder(AuctionDetailsActivity.this);
        builder.setView(viewFailedOfferDialog);
        final AlertDialog alertDialog = builder.create();

        backToAuctionButton.setOnClickListener(v -> alertDialog.dismiss());

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    private void showDownwardAuctionConfirmOfferDialog(){
        ConstraintLayout confirmOfferConstraintLayout = findViewById(R.id.confirmOfferConstraintLayout);
        View viewConfirmOfferDialog = LayoutInflater.from(AuctionDetailsActivity.this).inflate(R.layout.offer_confirm_dialog, confirmOfferConstraintLayout);

        Button confirmOfferButton = findViewById(R.id.confirmOfferButton);
        Button cancelOfferButton = findViewById(R.id.cancelOfferButton);

        TextView confirmOfferTitleText = findViewById(R.id.confirmOfferTitleText);
        confirmOfferTitleText.setText("Acquista");

        TextView confirmOfferText = findViewById(R.id.confirmOfferText);
        String confirmOffer = "Sicuro di voler acquistare "+auction.getTitle()+" per "+auction.getCurrentPrice()+"€?";
        confirmOfferText.setText(confirmOffer);

        AlertDialog.Builder builder = new AlertDialog.Builder(AuctionDetailsActivity.this);
        builder.setView(viewConfirmOfferDialog);
        final AlertDialog alertDialog = builder.create();

        confirmOfferButton.setOnClickListener(v -> {
            alertDialog.dismiss();
            makeDownwardOffer();
        });

        cancelOfferButton.setOnClickListener(v -> alertDialog.dismiss());

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();

    }

    private void showEnglishAuctionConfirmOfferDialog() {
        ConstraintLayout confirmOfferConstraintLayout = findViewById(R.id.confirmOfferConstraintLayout);
        View viewConfirmOfferDialog = LayoutInflater.from(AuctionDetailsActivity.this).inflate(R.layout.offer_confirm_dialog, confirmOfferConstraintLayout);

        Button confirmOfferButton = viewConfirmOfferDialog.findViewById(R.id.confirmOfferButton);
        Button cancelOfferButton = viewConfirmOfferDialog.findViewById(R.id.cancelOfferButton);

        TextView confirmOfferText = viewConfirmOfferDialog.findViewById(R.id.confirmOfferText);
        String confirmOffer = "Sicuro di voler offire "+offerEditText.getText().toString()+"€?";
        confirmOfferText.setText(confirmOffer);

        AlertDialog.Builder builder = new AlertDialog.Builder(AuctionDetailsActivity.this);
        builder.setView(viewConfirmOfferDialog);
        final AlertDialog alertDialog = builder.create();

        confirmOfferButton.setOnClickListener(v -> {
            makeEnglishOffer();
            alertDialog.dismiss();
        });

        cancelOfferButton.setOnClickListener(v -> alertDialog.dismiss());

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    private void hideOfferersSection() {
        offerersConstraintLayout.setVisibility(View.GONE);
    }

    private void makeDownwardOffer() {
        OfferDto offerDto = new OfferDto(auction.getCurrentPrice(), LocalDietiUser.getLocalDietiUser(getApplicationContext()).getId(), auction.getId());

        OfferAPI offerAPI = RetrofitService.getRetrofitInstance().create(OfferAPI.class);
        offerAPI.makeDownwardOffer(offerDto).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void makeEnglishOffer() {
        OfferDto offerDto = new OfferDto(new BigDecimal(offerEditText.getText().toString()), LocalDietiUser.getLocalDietiUser(getApplicationContext()).getId(), auction.getId());

        OfferAPI offerAPI = RetrofitService.getRetrofitInstance().create(OfferAPI.class);
        offerAPI.makeEnglishOffer(offerDto).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        refreshActivity();
    }

    private void refreshActivity() {
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(getIntent());
//        overridePendingTransition(0, 0);
        // ^commentato perché quando refresha fa vedere i valori di default, lascio per testare

        //questo invece è più veloce e si vedono per meno tempo i valori di default
        recreate();
    }

    private void initializeFields() {
        title.setText(auction.getTitle());
        categoryName.setText(auction.getCategory().toString());
        description.setText(auction.getDescription());
        currentPrice.setText(String.format("€%s", auction.getCurrentPrice().toString()));
        startTimer();
        String sellerInfo = auction.getOwner().getName() + " " + auction.getOwner().getSurname();
        sellerInfoText.setText(sellerInfo);
        if (auction instanceof EnglishAuction) {
            offerTextLayout.setHint(auction.getCurrentPrice().toString() + " + " + ((EnglishAuction) auction).getIncreaseAmount());
            BigDecimal newOffer = auction.getCurrentPrice().add(((EnglishAuction) auction).getIncreaseAmount());
            offerEditText.setText(String.format(newOffer.toString()));
        } else {
            offerEditText.setText(String.format(auction.getCurrentPrice().toString()));
            offerTextLayout.setHint("Prezzo");
        }
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
                    initializeFields();
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
                    initializeFields();
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
                Log.d("OFFERS", response.body().toString());
                if (response.code() == 200) {
                    if (response.body() != null)
                        offerrers.addAll(response.body());
                    initializeOfferrers();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Offer>> call, Throwable t) {
                Log.e("ERRORE", t.toString());
            }
        });
    }

    private void initializeOfferrers() {
        if (offerrers.isEmpty()) {
            messageNoOfferrers.setVisibility(View.VISIBLE);
            return;
        }

        messageNoOfferrers.setVisibility(View.GONE);
        Collections.reverse(offerrers);
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
        offerEditText.setFocusable(false);
        offerTextLayout = findViewById(R.id.offerTextLayout);
        makeAnOfferBtn = findViewById(R.id.makeAnOfferBtn);
        backBtn = findViewById(R.id.backBtn);
        sellerInfoBtn = findViewById(R.id.sellerInfo);
        sellerInfoText = findViewById(R.id.sellerInfoText);
        recyclerViewOfferrers = findViewById(R.id.offerrersList);
        offerersConstraintLayout = findViewById(R.id.offerrersConstraintLayout);

        messageNoOfferrers = findViewById(R.id.messageNoOfferrers);
        messageNoOfferrers.setVisibility(View.GONE);
    }
}
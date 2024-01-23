package it.unina.dietideals24.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
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
import it.unina.dietideals24.retrofit.api.ImageAPI;
import it.unina.dietideals24.retrofit.api.OfferAPI;
import it.unina.dietideals24.utils.ConvertSecondsToHourMinuteSeconds;
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;
import okhttp3.ResponseBody;
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
    private LinearLayout offerLinearLayout;
    private Auction auction;
    private ArrayList<Offer> offerrers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_details);

        initializeViews();
        offerrers = new ArrayList<>();

        Long idAuction = getIntent().getLongExtra("id", -1);
        String auctionType = getIntent().getStringExtra("type");

        if (auctionType != null && auctionType.equals("ENGLISH")) {
            makeAnOfferBtn.setText(R.string.make_an_offer_label);
            getEnglishAuction(idAuction);
            getOfferrersEnglishAuction(idAuction);
        } else if (auctionType != null && auctionType.equals("DOWNWARD")) {
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
        offerLinearLayout = findViewById(R.id.offerLinearLayout);

        messageNoOfferrers = findViewById(R.id.messageNoOfferrers);
        messageNoOfferrers.setVisibility(View.GONE);
    }

    private void refreshActivity() {
        recreate();
    }

    private void getEnglishAuction(Long idAuction) {
        EnglishAuctionAPI englishAuctionAPI = RetrofitService.getRetrofitInstance().create(EnglishAuctionAPI.class);
        englishAuctionAPI.getEnglishAuctionById(idAuction).enqueue(new Callback<EnglishAuction>() {
            @Override
            public void onResponse(Call<EnglishAuction> call, Response<EnglishAuction> response) {
                if (response.code() == 200 && response.body() != null) {
                    auction = response.body();
                    getAuctionImage(auction.getImageURL());
                }
            }

            @Override
            public void onFailure(Call<EnglishAuction> call, Throwable t) {
                Toast.makeText(AuctionDetailsActivity.this, "L'asta non è più disponibile!", Toast.LENGTH_SHORT).show();
                openMainActivity();
                finish();
            }
        });
    }

    private void getDownwardAuction(Long idAuction) {
        DownwardAuctionAPI downwardAuctionAPI = RetrofitService.getRetrofitInstance().create(DownwardAuctionAPI.class);
        downwardAuctionAPI.getDownwardAuctionById(idAuction).enqueue(new Callback<DownwardAuction>() {
            @Override
            public void onResponse(Call<DownwardAuction> call, Response<DownwardAuction> response) {
                if (response.code() == 200 && response.body() != null) {
                    auction = response.body();
                    getAuctionImage(auction.getImageURL());
                }
            }

            @Override
            public void onFailure(Call<DownwardAuction> call, Throwable t) {
                Toast.makeText(AuctionDetailsActivity.this, "L'asta non è più disponibile!", Toast.LENGTH_SHORT).show();
                openMainActivity();
                finish();
            }
        });
    }

    private void getAuctionImage(String imageURL) {
        ImageAPI imageAPI = RetrofitService.getRetrofitInstance().create(ImageAPI.class);
        imageAPI.getImageByUrl(imageURL).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        byte[] imageData = response.body().bytes();

                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions = requestOptions.transform(new CenterCrop());

                        Glide.with(getApplicationContext())
                                .load(bitmap)
                                .apply(requestOptions)
                                .into(image);
                    }
                    initializeFields();
                } catch (IOException e) {
                    Toast.makeText(AuctionDetailsActivity.this, "Impossibile caricare l'immagine'!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                initializeFields();
            }
        });
    }

    private void makeEnglishOffer() {
        OfferDto offerDto = new OfferDto(new BigDecimal(offerEditText.getText().toString()), LocalDietiUser.getLocalDietiUser(getApplicationContext()).getId(), auction.getId());

        OfferAPI offerAPI = RetrofitService.getRetrofitInstance().create(OfferAPI.class);
        offerAPI.makeEnglishOffer(offerDto).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(AuctionDetailsActivity.this, "Offerta fatta!", Toast.LENGTH_SHORT).show();
                refreshActivity();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showFailedOfferDialog("Offerta non effettuata, qualcuno è arrivato prima di te!");
                refreshActivity();
            }
        });
    }

    private void makeDownwardOffer() {
        OfferDto offerDto = new OfferDto(auction.getCurrentPrice(), LocalDietiUser.getLocalDietiUser(getApplicationContext()).getId(), auction.getId());

        OfferAPI offerAPI = RetrofitService.getRetrofitInstance().create(OfferAPI.class);
        offerAPI.makeDownwardOffer(offerDto).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(AuctionDetailsActivity.this, "Acquisto fatto!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showFailedOfferDialog("Acquisto non effettuato, qualcuno è arrivato prima di te!");
                finish();
            }
        });
    }

    private void getOfferrersEnglishAuction(Long idAuction) {
        OfferAPI offerAPI = RetrofitService.getRetrofitInstance().create(OfferAPI.class);
        offerAPI.getOffersByEnglishAuctionId(idAuction).enqueue(new Callback<ArrayList<Offer>>() {
            @Override
            public void onResponse(Call<ArrayList<Offer>> call, Response<ArrayList<Offer>> response) {
                if (response.code() == 200 && response.body() != null) {
                    offerrers.addAll(response.body());
                    initializeOfferrers();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Offer>> call, Throwable t) {
                Toast.makeText(AuctionDetailsActivity.this, "Impossibile caricare gli offerenti!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideOfferersSection() {
        offerersConstraintLayout.setVisibility(View.GONE);
    }

    private void initializeFields() {
        title.setText(auction.getTitle());
        categoryName.setText(auction.getCategory().toString());

        description.setText(auction.getDescription());
        description.setOnClickListener(v -> showBottomSheetDescription());

        currentPrice.setText(String.format("€%s", auction.getCurrentPrice().toString()));
        startTimer();
        String sellerInfo = auction.getOwner().getName() + " " + auction.getOwner().getSurname();
        sellerInfoText.setText(sellerInfo);

        if (auction instanceof EnglishAuction englishAuction) {
            offerTextLayout.setHint(auction.getCurrentPrice().toString() + " + " + englishAuction.getIncreaseAmount());
            BigDecimal newOffer = auction.getCurrentPrice().add(englishAuction.getIncreaseAmount());
            offerEditText.setText(String.format(newOffer.toString()));
        } else if (auction instanceof DownwardAuction downwardAuction) {
            offerEditText.setText(String.format(downwardAuction.getCurrentPrice().toString()));
            offerTextLayout.setHint("Prezzo");
        }

        if (auction.getOwner().equals(LocalDietiUser.getLocalDietiUser(getApplicationContext()))) {
            makeAnOfferBtn.setEnabled(false);
            offerLinearLayout.setVisibility(View.GONE);
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
                showEndedAuctionDialog("L'asta è terminata!");
            }
        }.start();
    }

    private void showEndedAuctionDialog(String errorMessage) {
        ConstraintLayout failedOfferConstraintLayout = findViewById(R.id.failedOfferConstraintLayout);
        View viewFailedOfferDialog = LayoutInflater.from(AuctionDetailsActivity.this).inflate(R.layout.failed_offer_dialog, failedOfferConstraintLayout);

        Button backToHomeButton = viewFailedOfferDialog.findViewById(R.id.backToAuctionBtn);

        TextView errorText = viewFailedOfferDialog.findViewById(R.id.failedOfferText);
        errorText.setText(errorMessage);

        AlertDialog.Builder builder = new AlertDialog.Builder(AuctionDetailsActivity.this);
        builder.setView(viewFailedOfferDialog);
        final AlertDialog alertDialog = builder.create();

        backToHomeButton.setText("Torna alla home");

        backToHomeButton.setOnClickListener(v -> {
            alertDialog.dismiss();
            finish();
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.setOnDismissListener(v -> openMainActivity());

        alertDialog.show();
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

    private void openMainActivity() {
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
    }

    private void showBottomSheetDescription() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet);

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        TextView descriptionText = dialog.findViewById(R.id.descriptionText);
        descriptionText.setText(auction.getDescription());

        cancelButton.setOnClickListener(view -> dialog.dismiss());
        descriptionText.setText(auction.getDescription());

        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    private void showFailedOfferDialog(String errorMessage) {
        ConstraintLayout failedOfferConstraintLayout = findViewById(R.id.failedOfferConstraintLayout);
        View viewFailedOfferDialog = LayoutInflater.from(AuctionDetailsActivity.this).inflate(R.layout.failed_offer_dialog, failedOfferConstraintLayout);

        Button backToAuctionButton = viewFailedOfferDialog.findViewById(R.id.backToAuctionBtn);

        TextView errorText = viewFailedOfferDialog.findViewById(R.id.failedOfferText);
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

    private void showDownwardAuctionConfirmOfferDialog() {
        ConstraintLayout confirmOfferConstraintLayout = findViewById(R.id.confirmOfferConstraintLayout);
        View viewConfirmOfferDialog = LayoutInflater.from(AuctionDetailsActivity.this).inflate(R.layout.offer_confirm_dialog, confirmOfferConstraintLayout);

        Button confirmOfferButton = viewConfirmOfferDialog.findViewById(R.id.confirmOfferButton);
        Button cancelOfferButton = viewConfirmOfferDialog.findViewById(R.id.cancelOfferButton);

        TextView confirmOfferTitleText = viewConfirmOfferDialog.findViewById(R.id.confirmOfferTitleText);
        confirmOfferTitleText.setText("Acquista");

        TextView confirmOfferText = viewConfirmOfferDialog.findViewById(R.id.confirmOfferText);
        String confirmOffer = "Sicuro di voler acquistare " + auction.getTitle() + " per " + auction.getCurrentPrice() + "€?";
        confirmOfferText.setText(confirmOffer);

        AlertDialog.Builder builder = new AlertDialog.Builder(AuctionDetailsActivity.this);
        builder.setView(viewConfirmOfferDialog);
        final AlertDialog alertDialog = builder.create();

        confirmOfferButton.setOnClickListener(v -> {
            makeDownwardOffer();
            alertDialog.dismiss();
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
        String confirmOffer = "Sicuro di voler offire " + offerEditText.getText().toString() + "€?";
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
}
package it.unina.dietideals24.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import it.unina.dietideals24.R;

public class AuctionDetailsActivity extends AppCompatActivity {

    ImageView backBtn, image;
    TextView title, categoryName, currentPrice, timer;
    EditText offerEditText;
    Button makeAnOfferBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_details);

        Long idAuction = getIntent().getLongExtra("id", -1);
        // TODO if idAuction == -1 -> Error

        InitializeViews();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InitializeViews() {
        image = findViewById(R.id.imageAcution);
        title = findViewById(R.id.titleAuction);
        categoryName = findViewById(R.id.categoryAuction);
        currentPrice = findViewById(R.id.currentPriceAuction);
        timer = findViewById(R.id.timerAuction);
        offerEditText = findViewById(R.id.inputAnOffer);
        makeAnOfferBtn = findViewById(R.id.makeAnOfferBtn);
        backBtn = findViewById(R.id.backBtn);
    }
}
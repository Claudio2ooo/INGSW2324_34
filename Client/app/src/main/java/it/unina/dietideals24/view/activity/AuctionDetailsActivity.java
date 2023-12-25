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

    ImageView backBtn;

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
        ImageView image = findViewById(R.id.imageAcution);
        TextView title = findViewById(R.id.titleAuction);
        TextView categoryName = findViewById(R.id.categoryAuction);
        TextView currentPrice = findViewById(R.id.currentPriceAuction);
        TextView timer = findViewById(R.id.timerAuction);
        EditText offerEditText = findViewById(R.id.inputAnOffer);
        Button makeAnOfferBtn = findViewById(R.id.makeAnOfferBtn);
        backBtn = findViewById(R.id.backBtn);
    }
}
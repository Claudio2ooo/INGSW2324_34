package it.unina.dietideals24.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import it.unina.dietideals24.R;

public class SearchAuctionActivity extends AppCompatActivity {

    private EditText searchAuctionEditText;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_auction);

        initializeViews();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeViews() {
        searchAuctionEditText = findViewById(R.id.inputSeachAuction);
        searchAuctionEditText.requestFocus();

        backBtn = findViewById(R.id.backBtn);
    }
}
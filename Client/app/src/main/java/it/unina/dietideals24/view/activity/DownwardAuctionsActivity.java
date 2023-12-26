package it.unina.dietideals24.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import it.unina.dietideals24.R;

public class DownwardAuctionsActivity extends AppCompatActivity {

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downward_auctions);

        InitializeViews();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InitializeViews() {
        backBtn = findViewById(R.id.backBtn);
    }
}
package it.unina.dietideals24.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import it.unina.dietideals24.R;

public class SellerInfoActivity extends AppCompatActivity {

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_info);

        initializeViews();

        backBtn.setOnClickListener(v -> finish());
    }

    private void initializeViews() {
        backBtn = findViewById(R.id.backBtn);
    }
}
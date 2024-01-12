package it.unina.dietideals24.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import it.unina.dietideals24.R;

public class PushNotificationActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);

        initializeViews();

        String data = getIntent().getStringExtra("data");
        textView.setText(data);
    }

    private void initializeViews() {
        textView = findViewById(R.id.textViewData);
    }
}
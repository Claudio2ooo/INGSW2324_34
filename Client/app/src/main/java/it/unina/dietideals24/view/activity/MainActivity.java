package it.unina.dietideals24.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import it.unina.dietideals24.R;
import it.unina.dietideals24.databinding.ActivityMainBinding;
import it.unina.dietideals24.view.fragment.AuctionFragment;
import it.unina.dietideals24.view.fragment.HomeFragment;
import it.unina.dietideals24.view.fragment.NotificationFragment;
import it.unina.dietideals24.view.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment(), "HOME");

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                replaceFragment(new HomeFragment(), "HOME");
            } else if (itemId == R.id.nav_auction) {
                replaceFragment(new AuctionFragment(), "AUCTION");
            } else if (itemId == R.id.nav_add) {
                showCreateAuctionDialog();
            } else if (itemId == R.id.nav_notify) {
                replaceFragment(new NotificationFragment(), "NOTIFY");
            } else if (itemId == R.id.nav_profile) {
                replaceFragment(new ProfileFragment(), "PROFILE");
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).addToBackStack(tag).commit();
    }

    private void showCreateAuctionDialog() {
        ConstraintLayout addAuctionConstraintLayout = findViewById(R.id.createAuctionConstraintLayout);
        View viewCreateAuctionDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.create_auction_dialog, addAuctionConstraintLayout);

        Button englishAuctionBtn = viewCreateAuctionDialog.findViewById(R.id.englishAuctionBtn);
        Button reverseAuctionBtn = viewCreateAuctionDialog.findViewById(R.id.downwardAuctionBtn);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(viewCreateAuctionDialog);
        final AlertDialog alertDialog = builder.create();

        englishAuctionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent createEnglishAuctionActivity = new Intent(getApplicationContext(), CreateEnglishAuctionActivity.class);
                startActivity(createEnglishAuctionActivity);
            }
        });

        reverseAuctionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent createDownwardAuctionActivity = new Intent(getApplicationContext(), CreateDownwardAuctionActivity.class);
                startActivity(createDownwardAuctionActivity);
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }
}
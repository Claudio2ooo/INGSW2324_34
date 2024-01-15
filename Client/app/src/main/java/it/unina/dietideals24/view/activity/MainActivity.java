package it.unina.dietideals24.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import it.unina.dietideals24.R;
import it.unina.dietideals24.databinding.ActivityMainBinding;
import it.unina.dietideals24.service.PushNotificationWorker;
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;
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

        askNotificationPermission();
        startNotificationWorker();

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

        backButtonManagement();
    }

    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }
    }

    private void startNotificationWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) //eseguito solo se connesso a internet
                .setRequiresBatteryNotLow(true)                //eseguito solo se batteria carica abbastanza
                .build();

        PeriodicWorkRequest pushNotificationWorker = new PeriodicWorkRequest.Builder(PushNotificationWorker.class, 1, TimeUnit.MINUTES)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag("pushNotifications")
                .setInputData(new Data.Builder()
                        .putLong("receiverId", LocalDietiUser.getLocalDietiUser(getApplicationContext()).getId())
                        .build())
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork("pushNotificationWorker", ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, pushNotificationWorker);
    }

    private void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).addToBackStack(tag).commit();
    }

    private void backButtonManagement() {
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) finish();

            FragmentManager.BackStackEntry backStackEntry = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);

            if (backStackEntry.getName() == null) return;

            if (backStackEntry.getName().equals("HOME")) {
                binding.bottomNavigation.getMenu().getItem(0).setChecked(true);
            } else if (backStackEntry.getName().equals("AUCTION")) {
                binding.bottomNavigation.getMenu().getItem(1).setChecked(true);
            } else if (backStackEntry.getName().equals("NOTIFY")) {
                binding.bottomNavigation.getMenu().getItem(3).setChecked(true);
            } else if (backStackEntry.getName().equals("PROFILE")) {
                binding.bottomNavigation.getMenu().getItem(4).setChecked(true);
            }
        });
    }

    private void showCreateAuctionDialog() {
        ConstraintLayout addAuctionConstraintLayout = findViewById(R.id.createAuctionConstraintLayout);
        View viewCreateAuctionDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.create_auction_dialog, addAuctionConstraintLayout);

        Button englishAuctionBtn = viewCreateAuctionDialog.findViewById(R.id.englishAuctionBtn);
        Button reverseAuctionBtn = viewCreateAuctionDialog.findViewById(R.id.downwardAuctionBtn);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(viewCreateAuctionDialog);
        final AlertDialog alertDialog = builder.create();

        englishAuctionBtn.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent createEnglishAuctionActivity = new Intent(getApplicationContext(), CreateEnglishAuctionActivity.class);
            startActivity(createEnglishAuctionActivity);
        });

        reverseAuctionBtn.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent createDownwardAuctionActivity = new Intent(getApplicationContext(), CreateDownwardAuctionActivity.class);
            startActivity(createDownwardAuctionActivity);
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }
}
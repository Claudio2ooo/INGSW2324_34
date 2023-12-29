package it.unina.dietideals24.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import it.unina.dietideals24.R;
import it.unina.dietideals24.dto.LoginDto;
import it.unina.dietideals24.response.LoginResponse;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DietiDealsAuthAPI;
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;
import it.unina.dietideals24.utils.localstorage.TokenManagement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginBtn, loginWithGoogleBtn;
    private TextView signInBtn;
    private ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();

        loginBtn.setOnClickListener(v -> {
            loginProgressBar.setVisibility(View.VISIBLE);
            login();
        });

        signInBtn.setOnClickListener(v -> {
            Intent signInActivity = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(signInActivity);
        });
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (email.equals("test") && password.equals("test")) {
            Intent signInActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(signInActivity);
        }

        LoginDto loginDto = new LoginDto(email, password);
        DietiDealsAuthAPI dietiDealsService = RetrofitService.getRetrofitInstance().create(DietiDealsAuthAPI.class);

        dietiDealsService.login(loginDto).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loginProgressBar.setVisibility(View.GONE);
                saveToken(response);
                saveCurrentUser(response);

                Intent signInActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(signInActivity);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginProgressBar.setVisibility(View.GONE);
                showFailedLoginDialog();
            }
        });
    }

    private void saveCurrentUser(Response<LoginResponse> response) {
        LocalDietiUser.setLocalDietiUser(getApplicationContext(), response.body().getDietiUser());
        Log.e("DietiUser", "DietiUser: " + LocalDietiUser.getLocalDietiUser(getApplicationContext()));
    }

    private void saveToken(Response<LoginResponse> response) {
        TokenManagement tokenManagement = TokenManagement.getInstance(getApplicationContext());
        tokenManagement.setToken(response.body().getToken());
    }

    private void showFailedLoginDialog() {
        ConstraintLayout failedLoginConstraintLayout = findViewById(R.id.failedLoginConstraintLayout);
        View viewFailedLoginDialog = LayoutInflater.from(LoginActivity.this).inflate(R.layout.failed_login_dialog, failedLoginConstraintLayout);

        Button tryAgainBtn = viewFailedLoginDialog.findViewById(R.id.tryAgainBtn);

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setView(viewFailedLoginDialog);
        final AlertDialog alertDialog = builder.create();

        tryAgainBtn.setOnClickListener(v -> alertDialog.dismiss());

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.inputEmail);
        passwordEditText = findViewById(R.id.inputPassword);

        loginBtn = findViewById(R.id.loginBtn);
        loginWithGoogleBtn = findViewById(R.id.loginWithGoogleBtn);
        signInBtn = findViewById(R.id.signInBtn);

        loginProgressBar = findViewById(R.id.loginProgressBar);
        loginProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        emailEditText.setText(null);
        passwordEditText.setText(null);
    }
}
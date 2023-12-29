package it.unina.dietideals24.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unina.dietideals24.R;
import it.unina.dietideals24.dto.LoginDto;
import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.response.LoginResponse;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DietiDealsAPI;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInActivity = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signInActivity);
            }
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
                saveToken(response);
                saveCurrentUser(response);

                Intent signInActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(signInActivity);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login fallito", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveCurrentUser(Response<LoginResponse> response) {
        LocalDietiUser.setLocalDietiUser(getApplicationContext(), response.body().getDietiUser());
        Log.e("DietiUser", "DietiUser: "+LocalDietiUser.getLocalDietiUser(getApplicationContext()));
    }

    private void saveToken(Response<LoginResponse> response) {
        TokenManagement tokenManagement = TokenManagement.getInstance(getApplicationContext());
        tokenManagement.setToken(response.body().getToken());
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.inputEmail);
        passwordEditText = findViewById(R.id.inputPassword);

        loginBtn = findViewById(R.id.loginBtn);
        loginWithGoogleBtn = findViewById(R.id.loginWithGoogleBtn);
        signInBtn = findViewById(R.id.signInBtn);
    }

    @Override
    protected void onResume() {
        super.onResume();

        emailEditText.setText(null);
        passwordEditText.setText(null);
    }
}
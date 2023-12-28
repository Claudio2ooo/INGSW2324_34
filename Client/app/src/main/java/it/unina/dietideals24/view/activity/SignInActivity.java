package it.unina.dietideals24.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import it.unina.dietideals24.R;
import it.unina.dietideals24.dto.RegisterDto;
import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.retrofit.DietiDealsService;
import it.unina.dietideals24.retrofit.DietiDealsServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private EditText nameEditText, surnameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signInBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initializeViews();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(everythingCompiled() && passwordCorrespond())
                    register();
            }
        });
    }

    private boolean passwordCorrespond() {
        String password = passwordEditText.getText().toString();
        String confirmedPassword = confirmPasswordEditText.getText().toString();

        return password.equals(confirmedPassword);
    }

    private boolean everythingCompiled() {
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmedPassowrd = confirmPasswordEditText.getText().toString();

        if(name.matches("")){
            nameEditText.setHintTextColor(Color.RED);
            return false;
        }
        if(surname.matches("")){
            surnameEditText.setHintTextColor(Color.RED);
            return false;
        }
        if(email.matches("")){
            emailEditText.setHintTextColor(Color.RED);
            return false;
        }
        if(password.matches("")){
            passwordEditText.setHintTextColor(Color.RED);
            return false;
        }
        if(confirmedPassowrd.matches("")){
            confirmPasswordEditText.setHintTextColor(Color.RED);
            return false;
        }

        return true;
    }

    private void register() {
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        RegisterDto registerDto = new RegisterDto(name, surname, email, password);
        DietiDealsService dietiDealsService = DietiDealsServiceGenerator.createService(DietiDealsService.class);
        Call<DietiUser> registerAsync = dietiDealsService.register(registerDto);
        registerAsync.enqueue(new Callback<DietiUser>() {
            @Override
            public void onResponse(Call<DietiUser> call, Response<DietiUser> response) {
                //TODO popup login successful
                finish();
            }

            @Override
            public void onFailure(Call<DietiUser> call, Throwable t) {
                //TODO popup login failed

            }
        });
    }

    private void initializeViews() {
        nameEditText = findViewById(R.id.inputName);
        surnameEditText = findViewById(R.id.inputSurname);
        emailEditText = findViewById(R.id.inputEmail);
        passwordEditText = findViewById(R.id.inputPassword);
        confirmPasswordEditText = findViewById(R.id.inputConfirmPassword);

        signInBtn = findViewById(R.id.signInBtn);
        backBtn = findViewById(R.id.backBtn);
    }

    @Override
    protected void onResume() {
        super.onResume();

        nameEditText.setText(null);
        surnameEditText.setText(null);
        emailEditText.setText(null);
        passwordEditText.setText(null);
        confirmPasswordEditText.setText(null);
    }
}
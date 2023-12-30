package it.unina.dietideals24.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.dto.EnglishAuctionDto;
import it.unina.dietideals24.enumerations.CategoryEnum;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.EnglishAuctionAPI;
import it.unina.dietideals24.utils.CategoryArrayListInitializer;
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEnglishAuctionActivity extends AppCompatActivity {

    private EditText nameEditText, descriptionEditText, startingPriceEditText, timerEditText, increaseAmountEditText;
    private TextInputLayout nameTextLayout, descriptionTextLayout, startingPriceTextLayout, timerTextLayout, increaseAmountTextLayout, categoryTextLayout;
    private ImageView backBtn, imageProduct;
    private Button createAuctionBtn, uploadImageBtn, cancelBtn;
    private ProgressBar createAuctionProgressBar;
    private AutoCompleteTextView listItemsDropdownMenu;
    private ActivityResultLauncher<PickVisualMediaRequest> singlePhotoPickerLauncher;
    private String selectedCategory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_english_auction);

        initializeSinglePhotoPickerLauncher();
        initializeViews();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the photo picker and let the user choose only images
                singlePhotoPickerLauncher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        createAuctionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotEmptyObligatoryFields())
                    createAuction();
            }
        });
    }

    private boolean isNotEmptyObligatoryFields() {
        String title = nameEditText.getText().toString();
        String startingPrice = startingPriceEditText.getText().toString();
        String timer = timerEditText.getText().toString();
        String increaseAmount = increaseAmountEditText.getText().toString();

        boolean ret = true;

        if (title.matches("")) {
            nameTextLayout.setError("Questo campo è obbligatorio");
            ret = false;
        } else
            nameTextLayout.setErrorEnabled(false);
        if (startingPrice.matches("")) {
            startingPriceTextLayout.setError("Questo campo è obbligatorio");
            ret = false;
        }
        if (timer.matches("")) {
            timerTextLayout.setError("Questo campo è obbligatorio");
            ret = false;
        }
        if(increaseAmount.matches("")) {
            increaseAmountTextLayout.setError("Questo campo è obbligatorio");
            ret = false;
        }
        if (selectedCategory == null) {
            categoryTextLayout.setError("Selezionare una categoria");
            ret = false;
        }

        return ret;
    }

    private void createAuction() {
        EnglishAuctionDto englishAuctionDto = new EnglishAuctionDto(
                nameEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                CategoryEnum.valueOf(selectedCategory.toUpperCase()),
                new BigDecimal(startingPriceEditText.getText().toString()),
                new BigDecimal(startingPriceEditText.getText().toString()),
                Long.valueOf(timerEditText.getText().toString())*1000,
                new BigDecimal(increaseAmountEditText.getText().toString()),
                LocalDietiUser.getLocalDietiUser(getApplicationContext()).getId()
        );

        EnglishAuctionAPI englishAuctionAPI = RetrofitService.getRetrofitInstance().create(EnglishAuctionAPI.class);
        englishAuctionAPI.createAuction(englishAuctionDto).enqueue(new Callback<EnglishAuction>() {
            @Override
            public void onResponse(Call<EnglishAuction> call, Response<EnglishAuction> response) {
                Log.e("Successful auction creation", "Asta creata " + response.body().getTitle());
                finish();
            }

            @Override
            public void onFailure(Call<EnglishAuction> call, Throwable t) {
                Log.e("Failed auction creation", "Creazione fallita");
            }
        });
    }

    private void initializeSinglePhotoPickerLauncher() {
        singlePhotoPickerLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                if (uri == null) {
                    Toast.makeText(CreateEnglishAuctionActivity.this, "Seleziona un'immagine!", Toast.LENGTH_SHORT).show();
                } else {
                    Glide.with(CreateEnglishAuctionActivity.this).load(uri).into(imageProduct);
                }
            }
        });
    }

    private void initializeViews() {
        backBtn = findViewById(R.id.backBtn);
        imageProduct = findViewById(R.id.imageProduct);
        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        createAuctionBtn = findViewById(R.id.createEnglishAuctionBtn);

        createAuctionProgressBar = findViewById(R.id.createEnglishAuctionProgressBar);
        createAuctionProgressBar.setVisibility(View.INVISIBLE);

        listItemsDropdownMenu = findViewById(R.id.listItemsDropdownMenu);
        categoryTextLayout = findViewById(R.id.categoryTextLayout);

        nameEditText = findViewById(R.id.inputName);
        nameTextLayout = findViewById(R.id.nameTextLayout);

        descriptionEditText = findViewById(R.id.inputDescription);
        descriptionTextLayout = findViewById(R.id.descriptionTextLayout);

        startingPriceEditText = findViewById(R.id.inputStartingPrice);
        startingPriceTextLayout = findViewById(R.id.startingPriceTextLayout);

        timerEditText = findViewById(R.id.inputTimer);
        timerTextLayout = findViewById(R.id.timerTextLayout);

        increaseAmountEditText = findViewById(R.id.inputIncreaseAmount);
        increaseAmountTextLayout = findViewById(R.id.increaseAmountTextLayout);

        initializeCategoryDropdownMenu();
    }

    private void initializeCategoryDropdownMenu() {
        ArrayList<String> categories = CategoryArrayListInitializer.getAllCategoryNames();

        ArrayAdapter<String> adapterItemListCategoryDropdownMenu = new ArrayAdapter<>(this, R.layout.category_item_dropdown_menu, categories);
        listItemsDropdownMenu.setAdapter(adapterItemListCategoryDropdownMenu);
        listItemsDropdownMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = adapterItemListCategoryDropdownMenu.getItem(position);
            }
        });
    }
}
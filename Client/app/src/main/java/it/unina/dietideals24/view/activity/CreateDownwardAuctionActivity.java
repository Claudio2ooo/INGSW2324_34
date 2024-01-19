package it.unina.dietideals24.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.dto.DownwardAuctionDto;
import it.unina.dietideals24.enumerations.CategoryEnum;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DownwardAuctionAPI;
import it.unina.dietideals24.utils.CategoryArrayListInitializer;
import it.unina.dietideals24.utils.MyFileUtils;
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateDownwardAuctionActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText startingPriceEditText;
    private EditText timerEditText;
    private EditText decreaseAmountEditText;
    private EditText minimumPriceEditText;
    private TextInputLayout nameTextLayout;
    private TextInputLayout descriptionTextLayout;
    private TextInputLayout categoryTextLayout;
    private TextInputLayout startingPriceTextLayout;
    private TextInputLayout timerTextLayout;
    private TextInputLayout decreaseAmountTextLayout;
    private TextInputLayout minimumPriceTextLayout;
    private ImageView backBtn;
    private ImageView imageProduct;
    private Button createAuctionBtn;
    private Button uploadImageBtn;
    private Button cancelBtn;
    private AutoCompleteTextView listItemsDropdownMenu;
    private ActivityResultLauncher<PickVisualMediaRequest> singlePhotoPickerLauncher;
    private ProgressBar createAuctionProgressBar;
    private String selectedCategory = null;
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_downward_auction);

        initializeSinglePhotoPickerLauncher();
        initializeViews();

        backBtn.setOnClickListener(v -> finish());
        cancelBtn.setOnClickListener(v -> finish());

        uploadImageBtn.setOnClickListener(v ->
                // Launch the photo picker and let the user choose only images
                singlePhotoPickerLauncher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build())
        );

        createAuctionBtn.setOnClickListener(v -> {
            if (isNotEmptyObligatoryFields() && isValidMinimumPrice()) {
                createAuctionProgressBar.setVisibility(View.VISIBLE);
                createAuction();
            }

        });

    }

    private boolean isValidMinimumPrice() {
        BigDecimal startingPrice = new BigDecimal(startingPriceEditText.getText().toString());
        BigDecimal minimumPrice = new BigDecimal(minimumPriceEditText.getText().toString());

        if (startingPrice.compareTo(minimumPrice) < 0) {
            minimumPriceTextLayout.setError("Il prezzo minimo deve essere minore del prezzo iniziale");
            return false;
        }
        minimumPriceTextLayout.setErrorEnabled(false);
        return true;
    }

    private boolean isNotEmptyObligatoryFields() {
        String title = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String startingPrice = startingPriceEditText.getText().toString();
        String timer = timerEditText.getText().toString();
        String decreaseAmount = decreaseAmountEditText.getText().toString();
        String minimumPrice = minimumPriceEditText.getText().toString();

        boolean ret = true;

        if (title.isEmpty()) {
            nameTextLayout.setError("Questo campo è obbligatorio");
            ret = false;
        } else
            nameTextLayout.setErrorEnabled(false);

        if (description.isEmpty()) {
            descriptionTextLayout.setError("Questo campo è obbligatorio");
            ret = false;
        } else
            descriptionTextLayout.setErrorEnabled(false);

        if (startingPrice.isEmpty()) {
            startingPriceTextLayout.setError("Questo campo è obbligatorio");
            ret = false;
        } else
            startingPriceTextLayout.setErrorEnabled(false);

        if (timer.isEmpty()) {
            timerTextLayout.setError("Questo campo è obbligatorio");
            ret = false;
        } else
            timerTextLayout.setErrorEnabled(false);

        if (Long.parseLong(timer) == 0) {
            timerTextLayout.setError("Il timer non può essere uguale a 0");
            ret = false;
        } else
            timerTextLayout.setErrorEnabled(false);

        if (decreaseAmount.isEmpty()) {
            decreaseAmountTextLayout.setError("Questo campo è obbligatorio");
            ret = false;
        } else
            decreaseAmountTextLayout.setErrorEnabled(false);

        if (minimumPrice.isEmpty()) {
            minimumPriceTextLayout.setError("Questo campo è obbligatorio");
        } else
            minimumPriceTextLayout.setErrorEnabled(false);

        if (selectedCategory == null) {
            categoryTextLayout.setError("Selezionare una categoria");
            ret = false;
        } else
            categoryTextLayout.setErrorEnabled(false);

        return ret;
    }

    private void createAuction() {
        DownwardAuctionDto downwardAuctionDto = new DownwardAuctionDto(
                nameEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                CategoryEnum.valueOf(selectedCategory.toUpperCase()),
                new BigDecimal(startingPriceEditText.getText().toString()),
                new BigDecimal(startingPriceEditText.getText().toString()),
                Long.parseLong(timerEditText.getText().toString()) * 1000,
                new BigDecimal(decreaseAmountEditText.getText().toString()),
                new BigDecimal(minimumPriceEditText.getText().toString()),
                LocalDietiUser.getLocalDietiUser(getApplicationContext()).getId()
        );

        DownwardAuctionAPI downwardAuctionAPI = RetrofitService.getRetrofitInstance().create(DownwardAuctionAPI.class);
        downwardAuctionAPI.createAuction(downwardAuctionDto).enqueue(new Callback<DownwardAuction>() {
            @Override
            public void onResponse(Call<DownwardAuction> call, Response<DownwardAuction> response) {
                if (response.body()!=null)
                    if (imageUri != null)
                        uploadImage(response.body().getId());
                    else {
                        createAuctionProgressBar.setVisibility(View.INVISIBLE);
                        showSuccessCreateAuctionDialog("Asta al ribasso \"" + downwardAuctionDto.getTitle() + "\" creata con successo");
                    }
            }

            @Override
            public void onFailure(Call<DownwardAuction> call, Throwable t) {
                createAuctionProgressBar.setVisibility(View.INVISIBLE);
                showFailedCreateAuctionDialog("Creazione asta \"" + downwardAuctionDto.getTitle() + "\" fallita!");

            }
        });

    }

    private void uploadImage(Long downwardAuctionId) {
        File imageToBeUploaded = MyFileUtils.uriToFile(imageUri, getApplicationContext());

        RequestBody requestBody = RequestBody.create(imageToBeUploaded, MediaType.parse("image/*"));
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", downwardAuctionId+".jpg", requestBody);

        DownwardAuctionAPI downwardAuctionAPI = RetrofitService.getRetrofitInstance().create(DownwardAuctionAPI.class);
        downwardAuctionAPI.uploadDownwardAuctionImage(downwardAuctionId, imagePart).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                createAuctionProgressBar.setVisibility(View.INVISIBLE);
                showSuccessCreateAuctionDialog("Asta al ribasso creata con successo");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                createAuctionProgressBar.setVisibility(View.INVISIBLE);
                showFailedCreateAuctionDialog("Caricamento immagine fallito");
            }
        });
    }

    private void initializeSinglePhotoPickerLauncher() {
        singlePhotoPickerLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri == null) {
                Toast.makeText(CreateDownwardAuctionActivity.this, "Seleziona un'immagine!", Toast.LENGTH_SHORT).show();
            } else {
                Glide.with(CreateDownwardAuctionActivity.this).load(uri).into(imageProduct);
                imageUri = uri;
            }
        });
    }

    private void initializeViews() {
        backBtn = findViewById(R.id.backBtn);
        imageProduct = findViewById(R.id.imageProduct);
        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        createAuctionBtn = findViewById(R.id.createDownwardAuctionBtn);

        createAuctionProgressBar = findViewById(R.id.createDownwardAuctionProgressBar);
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

        decreaseAmountEditText = findViewById(R.id.inputDecreaseAmount);
        decreaseAmountTextLayout = findViewById(R.id.decreaseAmountTextLayout);

        minimumPriceEditText = findViewById(R.id.inputMinimumPrice);
        minimumPriceTextLayout = findViewById(R.id.minimumPriceTextLayout);

        initializeCategoryDropdownMenu();
    }

    private void initializeCategoryDropdownMenu() {
        ArrayList<String> categories = CategoryArrayListInitializer.getAllCategoryNames();

        ArrayAdapter<String> adapterItemListCategoryDropdownMenu = new ArrayAdapter<>(this, R.layout.category_item_dropdown_menu, categories);
        listItemsDropdownMenu.setAdapter(adapterItemListCategoryDropdownMenu);
        listItemsDropdownMenu.setOnItemClickListener((parent, view, position, id) -> selectedCategory = adapterItemListCategoryDropdownMenu.getItem(position));
    }

    private void showSuccessCreateAuctionDialog(String messageText) {
        ConstraintLayout successCreateAuctionConstraintLayout = findViewById(R.id.successCreateAuctionConstraintLayout);
        View viewSuccessCreateAuctionDialog = LayoutInflater.from(CreateDownwardAuctionActivity.this).inflate(R.layout.success_create_auction_dialog, successCreateAuctionConstraintLayout);

        TextView messageTextView = viewSuccessCreateAuctionDialog.findViewById(R.id.successCreateAuctionText);
        messageTextView.setText(messageText);

        Button backToCreateAuctionBtn = viewSuccessCreateAuctionDialog.findViewById(R.id.backToCreateAuctionBtn);
        backToCreateAuctionBtn.setText(getResources().getString(R.string.create_another_downward_auction_label));

        Button backToHomeBtn = viewSuccessCreateAuctionDialog.findViewById(R.id.backToHomeBtn);

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateDownwardAuctionActivity.this);
        builder.setView(viewSuccessCreateAuctionDialog);
        final AlertDialog alertDialog = builder.create();

        backToCreateAuctionBtn.setOnClickListener(v -> {
            clearEditText();
            alertDialog.dismiss();
        });

        backToHomeBtn.setOnClickListener(v -> {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    private void showFailedCreateAuctionDialog(String messageText) {
        ConstraintLayout failedCreateAuctionConstraintLayout = findViewById(R.id.failedCreateAuctionConstraintLayout);
        View viewFailedCreateAuctionDialog = LayoutInflater.from(CreateDownwardAuctionActivity.this).inflate(R.layout.failed_create_auction_dialog, failedCreateAuctionConstraintLayout);

        TextView messageTextView = viewFailedCreateAuctionDialog.findViewById(R.id.failedCreateAuctionText);
        messageTextView.setText(messageText);

        Button backToCreateAuctionBtn = viewFailedCreateAuctionDialog.findViewById(R.id.backToCreateAuctionBtn);
        backToCreateAuctionBtn.setText(getResources().getString(R.string.create_another_downward_auction_label));

        Button backToHomeBtn = viewFailedCreateAuctionDialog.findViewById(R.id.backToHomeBtn);

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateDownwardAuctionActivity.this);
        builder.setView(viewFailedCreateAuctionDialog);
        final AlertDialog alertDialog = builder.create();

        backToCreateAuctionBtn.setOnClickListener(v -> alertDialog.dismiss());

        backToHomeBtn.setOnClickListener(v -> {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    private void clearEditText() {
        listItemsDropdownMenu.setText(null);
        nameEditText.setText(null);
        descriptionEditText.setText(null);
        startingPriceEditText.setText(null);
        timerEditText.setText(null);
        decreaseAmountEditText.setText(null);
        minimumPriceEditText.setText(null);
    }
}
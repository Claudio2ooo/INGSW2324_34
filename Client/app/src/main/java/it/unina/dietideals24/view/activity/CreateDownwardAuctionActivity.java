package it.unina.dietideals24.view.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.utils.CategoryArrayListInitializer;

public class CreateDownwardAuctionActivity extends AppCompatActivity {

    private EditText nameEditText, descriptionEditText, startingPriceEditText, timerEditText, decreaseAmountEditText, minimumPriceEditText;
    private ImageView backBtn, imageProduct;
    private Button createAuctionBtn, uploadImageBtn, cancelBtn;
    private AutoCompleteTextView listItemsDropdownMenu;
    private ActivityResultLauncher<PickVisualMediaRequest> singlePhotoPickerLauncher;
    private String selectedCategory = null;

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
    }

    private void initializeSinglePhotoPickerLauncher() {
        singlePhotoPickerLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri == null) {
                Toast.makeText(CreateDownwardAuctionActivity.this, "Seleziona un'immagine!", Toast.LENGTH_SHORT).show();
            } else {
                Glide.with(CreateDownwardAuctionActivity.this).load(uri).into(imageProduct);
            }
        });
    }

    private void initializeViews() {
        backBtn = findViewById(R.id.backBtn);
        imageProduct = findViewById(R.id.imageProduct);
        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        createAuctionBtn = findViewById(R.id.createDownwardAuctionBtn);

        listItemsDropdownMenu = findViewById(R.id.listItemsDropdownMenu);
        nameEditText = findViewById(R.id.inputName);
        descriptionEditText = findViewById(R.id.inputDescription);
        startingPriceEditText = findViewById(R.id.inputStartingPrice);
        timerEditText = findViewById(R.id.inputTimer);
        decreaseAmountEditText = findViewById(R.id.inputDecreaseAmount);
        minimumPriceEditText = findViewById(R.id.inputMinimumPrice);

        initializeCategoryDropdownMenu();
    }

    private void initializeCategoryDropdownMenu() {
        ArrayList<String> categories = CategoryArrayListInitializer.getAllCategoryNames();

        ArrayAdapter<String> adapterItemListCategoryDropdownMenu = new ArrayAdapter<>(this, R.layout.category_item_dropdown_menu, categories);
        listItemsDropdownMenu.setAdapter(adapterItemListCategoryDropdownMenu);
        listItemsDropdownMenu.setOnItemClickListener((parent, view, position, id) -> selectedCategory = adapterItemListCategoryDropdownMenu.getItem(position));
    }
}
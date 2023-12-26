package it.unina.dietideals24.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.adapter.CategoryAdapter;
import it.unina.dietideals24.entity.CategoryItem;
import it.unina.dietideals24.utils.CategoryArrayListInitializer;

public class CategoriesActivity extends AppCompatActivity {

    ArrayList<CategoryItem> categories;

    ImageView backBtn;
    AutoCompleteTextView listItemsDropdownMenu;
    String selectedCategory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        initializeViews();
        initializeCategories();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeViews() {
        backBtn = findViewById(R.id.backBtn);

        listItemsDropdownMenu = findViewById(R.id.listItemsDropdownMenu);
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
                ShowCategoryView(selectedCategory);
            }
        });
    }

    private void initializeCategories() {
        ArrayList<CategoryItem> categories = CategoryArrayListInitializer.getAllCategoryItems(getApplicationContext(), this);

        RecyclerView recyclerViewCategories = findViewById(R.id.categoryList);
        recyclerViewCategories.setLayoutManager(new GridLayoutManager(this, 3));

        RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> categoryAdapter = new CategoryAdapter(categories, false);
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    private void ShowCategoryView(String selectedCategory) {

    }
}
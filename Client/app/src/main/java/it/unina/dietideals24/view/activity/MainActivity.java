package it.unina.dietideals24.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.adapter.CategoryAdapter;
import it.unina.dietideals24.entity.CategoryItem;
import it.unina.dietideals24.enumerations.CategoryEnum;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter categoryAdapter;
    private RecyclerView recyclerViewCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCategory();
    }

    private void initCategory() {
        ArrayList<CategoryItem> categories = new ArrayList<>();

        for (CategoryEnum category : CategoryEnum.values()) {
            categories.add(new CategoryItem(capitalize(category.toString()), R.drawable.round_arrow_forward_24));
        }

        recyclerViewCategories = findViewById(R.id.categoryList);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        categoryAdapter = new CategoryAdapter(categories);
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
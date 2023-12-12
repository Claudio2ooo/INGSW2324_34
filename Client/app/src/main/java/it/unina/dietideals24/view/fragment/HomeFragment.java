package it.unina.dietideals24.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.adapter.CategoryAdapter;
import it.unina.dietideals24.entity.CategoryItem;
import it.unina.dietideals24.enumerations.CategoryEnum;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initCategory(view);

        return view;
    }

    private void initCategory(View view) {
        ArrayList<CategoryItem> categories = new ArrayList<>();

        getCategoryEnum(categories);

        RecyclerView recyclerViewCategories = view.findViewById(R.id.categoryList);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> categoryAdapter = new CategoryAdapter(categories);
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    /**
     * This method takes all the constants in the CategoryEnum and initializes the list categories array
     * @param categories containing the categories to be shown in the user interface
     * */
    private void getCategoryEnum(ArrayList<CategoryItem> categories) {
        CategoryEnum[] values = CategoryEnum.values();
        for (int i = 0; i < values.length && i < 6; i++) {
            CategoryEnum category = values[i];
            categories.add(new CategoryItem(capitalize(category.toString()), iconCategory(category.toString())));
        }
    }

    /**
     * This method converts the first character of a given string to uppercase; while the remainder is lowercase
     * @param str string to capitalise
     * */
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * this method automatically sets the icon string of a category, based on the input parameter, and returns it
     * @param category category name
     * */
    private int iconCategory(String category) {
        String iconName = "round_" + category.toLowerCase() + "_24";
        return getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName());
    }
}
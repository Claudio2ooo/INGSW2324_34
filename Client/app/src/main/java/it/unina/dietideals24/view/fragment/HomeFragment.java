package it.unina.dietideals24.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.adapter.AuctionAdapter;
import it.unina.dietideals24.adapter.CategoryAdapter;
import it.unina.dietideals24.entity.CategoryItem;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DownwardAuctionAPI;
import it.unina.dietideals24.retrofit.api.EnglishAuctionAPI;
import it.unina.dietideals24.utils.CategoryArrayListInitializer;
import it.unina.dietideals24.view.activity.CategoriesActivity;
import it.unina.dietideals24.view.activity.DownwardAuctionsActivity;
import it.unina.dietideals24.view.activity.EnglishAuctionsActivity;
import it.unina.dietideals24.view.activity.SearchAuctionActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private EditText searchAuctionEditText;
    private Button categoryBtn;
    private Button englishAuctionsBtn;
    private Button downwardAuctionsBtn;
    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewEnglishAuction;
    private RecyclerView recyclerViewDownwardAuction;
    private ProgressBar englishAuctionProgressBar;
    private ProgressBar downwardAuctionProgressBar;

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

        initializeViews(view);

        englishAuctionProgressBar.setVisibility(View.VISIBLE);

        initializeCategories();
        initializeEnglishAuction();
        initializeDownwardAuction();

        searchAuctionEditText.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchAuctionActivity.class);
            startActivity(intent);
        });

        categoryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CategoriesActivity.class);
            startActivity(intent);
        });

        englishAuctionsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EnglishAuctionsActivity.class);
            startActivity(intent);
        });

        downwardAuctionsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DownwardAuctionsActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeEnglishAuction();
        initializeDownwardAuction();
    }

    private void initializeViews(View view) {
        searchAuctionEditText = view.findViewById(R.id.inputSeachAuction);
        categoryBtn = view.findViewById(R.id.categoriesBtn);
        englishAuctionsBtn = view.findViewById(R.id.englishAuctionsBtn);
        downwardAuctionsBtn = view.findViewById(R.id.downwardAuctionsBtn);

        englishAuctionProgressBar = view.findViewById(R.id.englishAuctionProgressBar);
        englishAuctionProgressBar.setVisibility(View.VISIBLE);

        downwardAuctionProgressBar = view.findViewById(R.id.downwardAuctionProgressBar);
        downwardAuctionProgressBar.setVisibility(View.VISIBLE);

        recyclerViewCategories = view.findViewById(R.id.categoryList);
        recyclerViewEnglishAuction = view.findViewById(R.id.englishAuctionsList);
        recyclerViewDownwardAuction = view.findViewById(R.id.downwardAuctionsList);
    }

    private void initializeCategories() {
        ArrayList<CategoryItem> categories = CategoryArrayListInitializer.getFirstSixCategoryItems(getContext(), getActivity());

        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> categoryAdapter = new CategoryAdapter(categories, true);
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    private void initializeEnglishAuction() {
        recyclerViewEnglishAuction.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        EnglishAuctionAPI englishAuctionAPI = RetrofitService.getRetrofitInstance().create(EnglishAuctionAPI.class);
        englishAuctionAPI.getEnglishAuctions().enqueue(new Callback<ArrayList<Auction>>() {
            @Override
            public void onResponse(Call<ArrayList<Auction>> call, Response<ArrayList<Auction>> response) {
                ArrayList<Auction> auctions = response.body();

                englishAuctionProgressBar.setVisibility(View.GONE);
                RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> adapterAuction = new AuctionAdapter(auctions);
                recyclerViewEnglishAuction.setAdapter(adapterAuction);
            }

            @Override
            public void onFailure(Call<ArrayList<Auction>> call, Throwable t) {
                englishAuctionProgressBar.setVisibility(View.GONE);
                RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> adapterAuction = new AuctionAdapter(new ArrayList<>());
                recyclerViewEnglishAuction.setAdapter(adapterAuction);
            }
        });

    }

    private void initializeDownwardAuction() {
        recyclerViewDownwardAuction.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        DownwardAuctionAPI downwardAuctionAPI = RetrofitService.getRetrofitInstance().create(DownwardAuctionAPI.class);
        downwardAuctionAPI.getDownwardAuctions().enqueue(new Callback<ArrayList<Auction>>() {
            @Override
            public void onResponse(Call<ArrayList<Auction>> call, Response<ArrayList<Auction>> response) {
                ArrayList<Auction> auctions = response.body();

                downwardAuctionProgressBar.setVisibility(View.GONE);
                RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> adapterAuction = new AuctionAdapter(auctions);
                recyclerViewDownwardAuction.setAdapter(adapterAuction);
            }

            @Override
            public void onFailure(Call<ArrayList<Auction>> call, Throwable t) {
                downwardAuctionProgressBar.setVisibility(View.GONE);
                RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> adapterAuction = new AuctionAdapter(new ArrayList<>());
                recyclerViewDownwardAuction.setAdapter(adapterAuction);
            }
        });
    }
}
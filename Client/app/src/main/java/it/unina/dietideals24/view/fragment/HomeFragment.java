package it.unina.dietideals24.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.adapter.AuctionAdapter;
import it.unina.dietideals24.adapter.CategoryAdapter;
import it.unina.dietideals24.entity.CategoryItem;
import it.unina.dietideals24.enumerations.CategoryEnum;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.utils.CategoryArrayListInitializer;
import it.unina.dietideals24.view.activity.CategoriesActivity;
import it.unina.dietideals24.view.activity.DownwardAuctionsActivity;
import it.unina.dietideals24.view.activity.EnglishAuctionsActivity;
import it.unina.dietideals24.view.activity.SearchAuctionActivity;

public class HomeFragment extends Fragment {

    private EditText searchAuctionEditText;
    private Button categoryBtn, englishAuctionsBtn, downwardAuctionsBtn;

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
        initializeCategories(view);
        initializeEnglishAuction(view);
        initializeDownwardAuction(view);

        searchAuctionEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchAuctionActivity.class);
                startActivity(intent);
            }
        });

        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoriesActivity.class);
                startActivity(intent);
            }
        });

        englishAuctionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EnglishAuctionsActivity.class);
                startActivity(intent);
            }
        });

        downwardAuctionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DownwardAuctionsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initializeViews(View view) {
        searchAuctionEditText = view.findViewById(R.id.inputSeachAuction);
        categoryBtn = view.findViewById(R.id.categoriesBtn);
        englishAuctionsBtn = view.findViewById(R.id.englishAuctionsBtn);
        downwardAuctionsBtn = view.findViewById(R.id.downwardAuctionsBtn);
    }

    private void initializeCategories(View view) {
        ArrayList<CategoryItem> categories = CategoryArrayListInitializer.getAllCategoryItems(getContext(), getActivity());

        RecyclerView recyclerViewCategories = view.findViewById(R.id.categoryList);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> categoryAdapter = new CategoryAdapter(categories, true);
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    private void initializeEnglishAuction(View view) {
        // TEST AuctionAdapter
        ArrayList<Auction> auctions = new ArrayList<>();
        auctions.add(new Auction("Cuffie", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.INFORMATICA, "https://source.unsplash.com/random", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Computer", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.INFORMATICA, "https://source.unsplash.com/random", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Gioielli", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/featured", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Chitarra", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/featured", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Obiettivo", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/random", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Monitor", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/featured", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("TV", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/random", new BigDecimal(42), new BigDecimal(123), 1000L));

        RecyclerView recyclerViewAuction = view.findViewById(R.id.englishAuctionsList);
        recyclerViewAuction.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> adapterAuction = new AuctionAdapter(auctions);
        recyclerViewAuction.setAdapter(adapterAuction);
    }

    private void initializeDownwardAuction(View view) {
        // TEST AuctionAdapter
        ArrayList<Auction> auctions = new ArrayList<>();
        auctions.add(new Auction("Cuffie", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.INFORMATICA, "https://source.unsplash.com/random", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Computer", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.INFORMATICA, "https://source.unsplash.com/random", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Gioielli", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/featured", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Chitarra", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/featured", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Obiettivo", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/random", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("Monitor", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/featured", new BigDecimal(42), new BigDecimal(123), 1000L));
        auctions.add(new Auction("TV", "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", CategoryEnum.ELETTRONICA, "https://source.unsplash.com/random", new BigDecimal(42), new BigDecimal(123), 1000L));

        RecyclerView recyclerViewAuction = view.findViewById(R.id.downwardAuctionsList);
        recyclerViewAuction.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> adapterAuction = new AuctionAdapter(auctions);
        recyclerViewAuction.setAdapter(adapterAuction);
    }
}
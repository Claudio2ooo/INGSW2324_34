package it.unina.dietideals24.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import it.unina.dietideals24.R;
import it.unina.dietideals24.model.DietiUser;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.DietiUserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerInfoActivity extends AppCompatActivity {

    private TextView sellerFullNameText;
    private TextView geographicalAreaText;
    private TextView biographyText;
    private TextView linksText;
    private TextView titleSectionBiography;
    private TextView titleSectionLinks;
    private TextView messageNoInformation;
    private ImageView backBtn;
    private DietiUser seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_info);

        initializeViews();

        Long idSeller = getIntent().getLongExtra("id", -1);
        getSeller(idSeller);

        backBtn.setOnClickListener(v -> finish());
    }

    private void getSeller(Long id) {
        DietiUserAPI dietiUserAPI = RetrofitService.getRetrofitInstance().create(DietiUserAPI.class);
        dietiUserAPI.getUserById(id).enqueue(new Callback<DietiUser>() {
            @Override
            public void onResponse(Call<DietiUser> call, Response<DietiUser> response) {
                if (response.code() == 200) {
                    seller = response.body();
                    initializeFields();
                }
            }

            @Override
            public void onFailure(Call<DietiUser> call, Throwable t) {

            }
        });
    }

    private void initializeFields() {
        sellerFullNameText.setText(String.format("%s %s", seller.getName(), seller.getSurname()));

        if (seller.getGeographicalArea().isEmpty() || seller.getBiography().isEmpty() || seller.getLinks().isEmpty()) {
            messageNoInformation.setVisibility(View.VISIBLE);

            titleSectionBiography.setVisibility(View.GONE);
            titleSectionLinks.setVisibility(View.GONE);

            geographicalAreaText.setVisibility(View.GONE);
            biographyText.setVisibility(View.GONE);
            linksText.setVisibility(View.GONE);
        } else {
            messageNoInformation.setVisibility(View.GONE);

            geographicalAreaText.setText(seller.getGeographicalArea());
            biographyText.setText(seller.getBiography());

            StringBuilder links = new StringBuilder();
            for (String link : seller.getLinks()) {
                links.append(link);
            }
            linksText.setText(links);
        }
    }

    private void initializeViews() {
        sellerFullNameText = findViewById(R.id.sellerFullNameText);
        geographicalAreaText = findViewById(R.id.geographicalAreaSellerText);
        biographyText = findViewById(R.id.biographySellerText);
        linksText = findViewById(R.id.linksSellerText);

        titleSectionBiography = findViewById(R.id.titleSectionBiographySeller);
        titleSectionLinks = findViewById(R.id.titleSectionLinksSeller);
        messageNoInformation = findViewById(R.id.messageNoInformation);
        messageNoInformation.setVisibility(View.GONE);

        backBtn = findViewById(R.id.backBtn);
    }
}
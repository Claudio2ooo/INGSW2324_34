package it.unina.dietideals24.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.ImageAPI;
import it.unina.dietideals24.utils.ConvertSecondsToHourMinuteSeconds;
import it.unina.dietideals24.view.activity.AuctionDetailsActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> {
    ArrayList<Auction> auctions;
    Context context;
    int layout;

    public enum OrientationEnum {VERTICAL, HORIZONTAL}

    OrientationEnum orientation;

    /**
     * Constructor of AuctionAdapter
     *
     * @param auctions    list of auctions to put into the Adapter
     * @param orientation orientation of the Adapter to use, use public (static) enum OrientationEnum with constants VERTICAL or HORIZONTAL
     */
    public AuctionAdapter(ArrayList<Auction> auctions, OrientationEnum orientation) {
        this.auctions = auctions;
        this.orientation = orientation;
    }

    @NonNull
    @Override
    public AuctionAdapter.AuctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (orientation.equals(OrientationEnum.VERTICAL))
            layout = R.layout.auction_item;
        else if (orientation.equals(OrientationEnum.HORIZONTAL))
            layout = R.layout.auction_item_horizontal;

        View inflate = LayoutInflater.from(context).inflate(layout, parent, false);
        return new AuctionAdapter.AuctionViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AuctionAdapter.AuctionViewHolder holder, int position) {
        holder.title.setText(auctions.get(holder.getAdapterPosition()).getTitle());
        holder.categoryName.setText(auctions.get(holder.getAdapterPosition()).getCategory().toString());
        holder.currentPrice.setText(String.format("€%s", auctions.get(holder.getAdapterPosition()).getCurrentPrice().toString()));

        if (!holder.timerStarted)
            startTimer(holder);

        holder.showAuctionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), AuctionDetailsActivity.class);
            intent.putExtra("id", auctions.get(holder.getAdapterPosition()).getId());

            if (auctions.get(holder.getAdapterPosition()) instanceof EnglishAuction)
                intent.putExtra("type", "ENGLISH");
            else if (auctions.get(holder.getAdapterPosition()) instanceof DownwardAuction)
                intent.putExtra("type", "DOWNWARD");

            context.startActivity(intent);
        });

        retrieveImage(holder);
    }

    private void retrieveImage(AuctionViewHolder holder) {
        String imageUrl = auctions.get(holder.getAdapterPosition()).getImageURL();

        if (imageUrl == null)
            retrieveDefaultImage(holder);
        else
            retrieveAuctionImage(holder, imageUrl);
    }

    private void retrieveAuctionImage(AuctionViewHolder holder, String imageUrl) {
        ImageAPI imageAPI = RetrofitService.getRetrofitInstance().create(ImageAPI.class);
        imageAPI.getImageByUrl(imageUrl).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        byte[] imageData = response.body().bytes();

                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions = requestOptions.transform(new CenterCrop());

                        Glide.with(context)
                                .load(bitmap)
                                .apply(requestOptions)
                                .into(holder.image);
                    }
                } catch (IOException e) {

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void retrieveDefaultImage(AuctionViewHolder holder) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());

        Glide.with(context)
                .load(R.drawable.no_image_auction)
                .apply(requestOptions)
                .into(holder.image);
    }

    private void startTimer(AuctionAdapter.AuctionViewHolder holder) {
        holder.timerStarted = true;
        Timestamp creation = new Timestamp(auctions.get(holder.getAdapterPosition()).getCreatedAt().getTime());
        Timestamp deadline = new Timestamp(creation.getTime() + auctions.get(holder.getAdapterPosition()).getTimerInMilliseconds());

        new CountDownTimer(deadline.getTime() - System.currentTimeMillis(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                holder.timer.setText(ConvertSecondsToHourMinuteSeconds.formatSeconds(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if (!auctions.isEmpty()) {
                    auctions.remove(holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return auctions.size();
    }

    public class AuctionViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        TextView title;
        TextView categoryName;
        TextView currentPrice;
        TextView timer;
        Button showAuctionBtn;
        boolean timerStarted;

        public AuctionViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageItem);
            title = itemView.findViewById(R.id.titleItem);
            categoryName = itemView.findViewById(R.id.categoryName);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            timer = itemView.findViewById(R.id.timer);
            timerStarted = false;

            showAuctionBtn = itemView.findViewById(R.id.showAuctionBtn);
        }
    }
}

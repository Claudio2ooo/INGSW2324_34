package it.unina.dietideals24.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.model.Auction;
import it.unina.dietideals24.model.DownwardAuction;
import it.unina.dietideals24.model.EnglishAuction;
import it.unina.dietideals24.view.activity.AuctionDetailsActivity;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> {
    ArrayList<Auction> auctions;
    Context context;

    public AuctionAdapter(ArrayList<Auction> auctions) {
        this.auctions = auctions;
    }

    @NonNull
    @Override
    public AuctionAdapter.AuctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.auction_item, parent, false);
        return new AuctionAdapter.AuctionViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AuctionAdapter.AuctionViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Glide.with(context)
                .load(auctions.get(holder.getAdapterPosition()).getImageURL())
                .apply(requestOptions)
                .into(holder.image);

        holder.title.setText(auctions.get(holder.getAdapterPosition()).getTitle());
        holder.categoryName.setText(auctions.get(holder.getAdapterPosition()).getCategory().toString());
        holder.currentPrice.setText(auctions.get(holder.getAdapterPosition()).getCurrentPrice().toString());
        holder.timer.setText(auctions.get(holder.getAdapterPosition()).getTimerInMilliseconds().toString());

        holder.showAuctionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), AuctionDetailsActivity.class);
                intent.putExtra("id", auctions.get(holder.getAdapterPosition()).getId());
                if (auctions.get(holder.getAdapterPosition()) instanceof EnglishAuction)
                    intent.putExtra("type", "ENGLISH");
                else if (auctions.get(holder.getAdapterPosition()) instanceof DownwardAuction)
                    intent.putExtra("type", "DOWNWARD");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return auctions.size();
    }

    public class AuctionViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, categoryName, currentPrice, timer;
        Button showAuctionBtn;

        public AuctionViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageItem);
            title = itemView.findViewById(R.id.titleItem);
            categoryName = itemView.findViewById(R.id.categoryName);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            timer = itemView.findViewById(R.id.timer);

            showAuctionBtn = itemView.findViewById(R.id.showAuctionBtn);
        }
    }
}

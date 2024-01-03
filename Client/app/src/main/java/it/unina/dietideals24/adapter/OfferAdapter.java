package it.unina.dietideals24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import it.unina.dietideals24.model.Offer;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.SellerViewHolder> {
    ArrayList<Offer> offerrers;
    Context context;

    public OfferAdapter(ArrayList<Offer> offerrers) {
        this.offerrers = offerrers;
    }

    @NonNull
    @Override
    public OfferAdapter.SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.offer_item, parent, false);
        return new OfferAdapter.SellerViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.SellerViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Glide.with(context)
                .load(offerrers.get(holder.getAdapterPosition()).getOfferer().getProfilePictureUrl())
                .apply(requestOptions)
                .into(holder.image);

        String nameOffer = offerrers.get(holder.getAdapterPosition()).getOfferer().getName();
        String surnameOffer = offerrers.get(holder.getAdapterPosition()).getOfferer().getSurname();
        holder.fullName.setText(String.format("%s %s", nameOffer, surnameOffer));
        holder.amount.setText(String.format("€%s", offerrers.get(holder.getAdapterPosition()).getAmount().toString()));
    }

    @Override
    public int getItemCount() {
        return offerrers.size();
    }

    public class SellerViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView fullName;
        TextView amount;

        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageSeller);
            fullName = itemView.findViewById(R.id.fullNameSeller);
            amount = itemView.findViewById(R.id.amountSeller);
        }
    }
}

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
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;

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

        String emailOffer = offerrers.get(holder.getAdapterPosition()).getOfferer().getEmail();
        if (!emailOffer.equals(LocalDietiUser.getLocalDietiUser(context).getEmail()))
            holder.email.setText(String.format(replacingCharactersWithAsterisks(emailOffer)));
        else
            holder.email.setText(String.format(emailOffer));

        if (position == 0)
            holder.email.setTextColor(context.getResources().getColor(R.color.green_pistachio, context.getTheme()));
        else
            holder.email.setTextColor(context.getResources().getColor(R.color.white, context.getTheme()));

        holder.amount.setText(String.format("€%s", offerrers.get(holder.getAdapterPosition()).getAmount().toString()));
    }

    @Override
    public int getItemCount() {
        return offerrers.size();
    }

    public class SellerViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView email;
        TextView amount;

        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageOffer);
            email = itemView.findViewById(R.id.emailOffer);
            amount = itemView.findViewById(R.id.amountOffer);
        }
    }

    private String replacingCharactersWithAsterisks(String str) {
        return str.replaceFirst("\\b(\\w)\\S*?(\\S@)(\\S)\\S*(\\S\\.\\S*)\\b", "$1****$2$3****$4");
    }
}

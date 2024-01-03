package it.unina.dietideals24.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.entity.CategoryItem;
import it.unina.dietideals24.view.activity.AuctionsByCategoryActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    ArrayList<CategoryItem> categories;
    boolean isRound;
    Context context;

    public CategoryAdapter(ArrayList<CategoryItem> categories, boolean isRound) {
        this.categories = categories;
        this.isRound = isRound;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View inflate;
        if (isRound)
            inflate = LayoutInflater.from(context).inflate(R.layout.category_round_item, parent, false);
        else
            inflate = LayoutInflater.from(context).inflate(R.layout.category_square_item, parent, false);

        return new CategoryViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.nameCategory.setText(categories.get(holder.getAdapterPosition()).getName());
        holder.imageCategory.setImageResource(categories.get(holder.getAdapterPosition()).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), AuctionsByCategoryActivity.class);
                intent.putExtra("category", categories.get(holder.getAdapterPosition()).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView nameCategory;
        ImageView imageCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            nameCategory = itemView.findViewById(R.id.nameCategory);
            imageCategory = itemView.findViewById(R.id.imageCategory);
        }
    }
}

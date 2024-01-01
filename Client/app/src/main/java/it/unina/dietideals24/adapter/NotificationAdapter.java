package it.unina.dietideals24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.unina.dietideals24.R;
import it.unina.dietideals24.model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    ArrayList<Notification> notifications;
    Context context;

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.NotificationViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {

        holder.deleteBtn.setOnClickListener(v -> notifications.remove(holder.getAdapterPosition()));

        if (notifications.get(holder.getAdapterPosition()).getState().toString().equals("VINTA")) {
            holder.stateTextView.setText("VINTA");
            holder.stateTextView.setBackgroundColor(context.getResources().getColor(R.color.green_pistachio, context.getTheme()));
        } else if (notifications.get(holder.getAdapterPosition()).getState().toString().equals("PERSA")) {
            holder.stateTextView.setText("PERSA");
            holder.stateTextView.setBackgroundColor(context.getResources().getColor(R.color.red_rose, context.getTheme()));
        } else {
            holder.stateTextView.setText("FALLITA");
            holder.stateTextView.setBackgroundColor(context.getResources().getColor(R.color.yellow, context.getTheme()));
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView deleteBtn;
        TextView titleTextView;
        TextView priceTextView;
        TextView stateTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageItem);
            titleTextView = itemView.findViewById(R.id.titleItemNotification);
            priceTextView = itemView.findViewById(R.id.priceItemNotification);
            deleteBtn = itemView.findViewById(R.id.deleteNotificationBtn);
            stateTextView = itemView.findViewById(R.id.stateText);
        }
    }
}

package it.unina.dietideals24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unina.dietideals24.R;
import it.unina.dietideals24.enumerations.StateEnum;
import it.unina.dietideals24.model.Notification;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.NotificationAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    List<Notification> notifications;
    Context context;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.NotificationViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {

        holder.deleteBtn.setOnClickListener(v -> deleteNotification(holder.getAdapterPosition()));

        switch (notifications.get(holder.getAdapterPosition()).getState()) {
            case VINTA -> {
                holder.stateTextView.setText(StateEnum.VINTA.toString());
                holder.stateTextView.setBackgroundColor(context.getResources().getColor(R.color.green_pistachio, context.getTheme()));
            }
            case PERSA -> {
                holder.stateTextView.setText(StateEnum.PERSA.toString());
                holder.stateTextView.setBackgroundColor(context.getResources().getColor(R.color.red_rose, context.getTheme()));
            }
            case FALLITA -> {
                holder.stateTextView.setText(StateEnum.FALLITA.toString());
                holder.stateTextView.setBackgroundColor(context.getResources().getColor(R.color.red_rose, context.getTheme()));
            }
            case CONCLUSA -> {
                holder.stateTextView.setText(StateEnum.CONCLUSA.toString());
                holder.stateTextView.setBackgroundColor(context.getResources().getColor(R.color.yellow, context.getTheme()));
            }
        }
        holder.titleTextView.setText(notifications.get(holder.getAdapterPosition()).getTitleOfTheAuction());
        holder.priceTextView.setText(String.format(notifications.get(holder.getAdapterPosition()).getFinalPrice().toString()));
    }

    private void deleteNotification(int adapterPosition) {
        notifications.remove(adapterPosition);
        NotificationAPI notificationAPI = RetrofitService.getRetrofitInstance().create(NotificationAPI.class);
        notificationAPI.deleteNotificationById(notifications.get(adapterPosition).getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
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

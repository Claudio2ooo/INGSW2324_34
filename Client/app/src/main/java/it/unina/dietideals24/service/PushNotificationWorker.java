package it.unina.dietideals24.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import it.unina.dietideals24.R;
import it.unina.dietideals24.model.Notification;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.NotificationAPI;
import it.unina.dietideals24.view.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushNotificationWorker extends Worker {
    public PushNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        long tokenExpiration = getInputData().getLong("tokenExpiration", 0);

        if (tokenExpiration < System.currentTimeMillis()) {
            WorkManager.getInstance(getApplicationContext()).cancelUniqueWork("pushNotificationWorker");
            return Result.failure();
        }

        fetchNotification(getInputData().getLong("receiverId", -1));
        return Result.success();
    }

    private void fetchNotification(long receiverId) {
        if (receiverId == -1)
            return;

        NotificationAPI notificationAPI = RetrofitService.getRetrofitInstance().create(NotificationAPI.class);
        notificationAPI.getPushNotificationByReceiverId(receiverId).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.body() != null) {
                    pushNotifications(response.body());
                    MainActivity.setIsVisibleBadgeNotification(true);
                } else {
                    MainActivity.setIsVisibleBadgeNotification(false);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                MainActivity.setIsVisibleBadgeNotification(false);
            }
        });
    }

    private void pushNotifications(List<Notification> notifications) {
        if (notifications.size() > 3) {
            pushManyNotifications();
        } else {
            for (Notification notification : notifications) {
                pushNotification(notification);
            }
        }
    }

    private void pushNotification(Notification notification) {
        String channelId = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        builder
                .setSmallIcon(R.drawable.ic_gavel_notification)
                .setContentTitle(notification.getTitleOfTheAuction() + " terminata!")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        switch (notification.getState()) {
            case VINTA -> builder
                    .setContentText("Aggiudicato!")
                    .setColor(getApplicationContext().getResources().getColor(R.color.green_pistachio, getApplicationContext().getTheme()));
            case PERSA -> builder
                    .setContentText("Non ti sei aggiudicato l'asta")
                    .setColor(getApplicationContext().getResources().getColor(R.color.red_rose, getApplicationContext().getTheme()));
            case CONCLUSA -> builder
                    .setContentText("L'asta è terminata")
                    .setColor(getApplicationContext().getResources().getColor(R.color.yellow, getApplicationContext().getTheme()));
            case FALLITA -> builder
                    .setContentText("Asta fallita")
                    .setColor(getApplicationContext().getResources().getColor(R.color.red_error, getApplicationContext().getTheme()));
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("redirect", "notificationFragment");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
        if (notificationChannel == null) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            notificationChannel = new NotificationChannel(channelId, "Auction push notification", importance);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }

    private void pushManyNotifications() {
        String channelId = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        builder
                .setSmallIcon(R.drawable.ic_gavel_notification)
                .setContentTitle("Ci sono novità!")
                .setContentText("Varie aste sono terminate, controlla come sono andate")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("redirect", "notificationFragment");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);
        if (notificationChannel == null) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            notificationChannel = new NotificationChannel(channelId, "ManyNotifications", importance);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }
}

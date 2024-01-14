package it.unina.dietideals24.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

import it.unina.dietideals24.model.Notification;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.NotificationAPI;
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushNotificationService extends Service {

    //TODO eliminare questa classe se usiamo i Worker

    private static Runnable runnable = null;
    private Handler handler = new Handler();
    private NotificationAPI notificationAPI;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationAPI = RetrofitService.getRetrofitInstance().create(NotificationAPI.class);

        Log.e("Service", "PushNotificationService created!");

        runnable = () -> {
            Log.e("Service", "PushNotificationService is still running!");
            fetchNotifications();
            handler.postDelayed(runnable, 60000);
        };
        handler.postDelayed(runnable, 60000);
    }

    private void fetchNotifications() {
        notificationAPI
                .getNotificationByReceiverId(LocalDietiUser.getLocalDietiUser(getApplicationContext()).getId())
                .enqueue(new Callback<List<Notification>>() {
                    @Override
                    public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                        if (response.body() != null) {
                            pushNotification(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Notification>> call, Throwable t) {

                    }
                });
    }

    private void pushNotification(List<Notification> notifications) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Service", "PushNotificationService was stopped");
    }
}

package it.unina.dietideals24.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import it.unina.dietideals24.model.Notification;
import it.unina.dietideals24.retrofit.RetrofitService;
import it.unina.dietideals24.retrofit.api.NotificationAPI;
import it.unina.dietideals24.utils.localstorage.LocalDietiUser;
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
                    pushNotifiations(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {

            }
        });
    }

    private void pushNotifiations(List<Notification> notifications) {
        //codicePerNotificaPush.txt
    }
}

package it.unina.dietideals24.utils.localstorage;

import android.content.Context;
import android.content.SharedPreferences;

import it.unina.dietideals24.model.DietiUser;

public class LocalDietiUser {

    public static void setLacalDietiUser(Context context, DietiUser dietiUser) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("local_dieti_user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", String.valueOf(dietiUser.getId()));
        editor.putString("name", dietiUser.getName());
        editor.putString("surname", dietiUser.getSurname());
        editor.putString("email", dietiUser.getEmail());
        editor.putString("links", dietiUser.getLinks().toString());
        editor.putString("geographicalArea", dietiUser.getGeographicalArea());
        editor.apply();
    }

    public static void deleteLocalDietiUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("local_dieti_user_data", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}

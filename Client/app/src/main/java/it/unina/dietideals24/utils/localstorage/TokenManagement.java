package it.unina.dietideals24.utils.localstorage;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManagement {
    private static TokenManagement token = null;
    private static SharedPreferences sharedPreferences = null;
    private static final String TOKEN_KEY = "token";
    private static final String EXPIRATION ="expiration";

    private TokenManagement(Context context) {
        sharedPreferences = context.getSharedPreferences("token_data", Context.MODE_PRIVATE);
    }

    public static synchronized TokenManagement getInstance(Context context) {
        if (token == null) {
            token = new TokenManagement(context);
        }
        return token;
    }

    public void setToken(String token, long expiresIn) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply();
        sharedPreferences.edit().putLong(EXPIRATION, System.currentTimeMillis()+expiresIn).apply();
    }

    public static String getToken() {
        if (sharedPreferences == null)
            return "";
        return sharedPreferences.getString(TOKEN_KEY, "");
    }

    public static void deleteToken() {
        if (sharedPreferences != null)
            sharedPreferences.edit().remove(TOKEN_KEY).apply();
    }

    public static boolean isExpired() {
        if (sharedPreferences != null) {
            if (sharedPreferences.getLong(EXPIRATION, 0) < System.currentTimeMillis())
                return true;
            return false;
        }
        return true;
    }
}

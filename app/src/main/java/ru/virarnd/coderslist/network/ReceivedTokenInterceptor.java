package ru.virarnd.coderslist.network;

import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedTokenInterceptor implements Interceptor {

//    private static final String TAG = ReceivedTokenInterceptor.class.getSimpleName();
    private SharedPreferences preferences;

    public ReceivedTokenInterceptor(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String token = response.header("Token");
        if (token != null && !token.isEmpty()) {
            preferences.edit().putString("token", token).apply();
        }
        return response;
    }
}

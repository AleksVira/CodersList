package ru.virarnd.coderslist.network;

import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddTokenInterceptor implements Interceptor {

    private SharedPreferences preferences;

    public AddTokenInterceptor(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = preferences.getString("Token", "");
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (!token.isEmpty()) {
            requestBuilder.addHeader("X-Token", token);
        }
        return chain.proceed(requestBuilder.build());
    }
}

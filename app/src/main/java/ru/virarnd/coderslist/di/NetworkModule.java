package ru.virarnd.coderslist.di;

import android.content.Context;

import com.readystatesoftware.chuck.ChuckInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.virarnd.coderslist.models.github.GithubUsersService;
import ru.virarnd.coderslist.models.overflow.OverflowUsersService;

@Module
public class NetworkModule {

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(Context context) {
        ChuckInterceptor chuckInterceptor = new ChuckInterceptor(context);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addNetworkInterceptor(chuckInterceptor)
                .build();
    }

    @Provides
    @PerApplication
    GithubUsersService provideGitHubService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubUsersService.class);
    }

    @Provides
    @PerApplication
    OverflowUsersService provideOverflowService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/2.2/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(OverflowUsersService.class);
    }


}

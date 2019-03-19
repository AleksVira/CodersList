package ru.virarnd.coderslist;

import android.app.Application;

import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.virarnd.coderslist.models.github.GithubUsersService;
import ru.virarnd.coderslist.models.overflow.OverflowUsersService;

public class App extends Application {

    GithubUsersService githubUsersService;
    OverflowUsersService overflowUsersService;

    public GithubUsersService getGithubUsersService() {
        return githubUsersService;
    }

    public OverflowUsersService getOverflowUsersService() {
        return overflowUsersService;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        createNetworkServices();
    }

    private void createNetworkServices() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        ChuckInterceptor chuckInterceptor = new ChuckInterceptor(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addNetworkInterceptor(chuckInterceptor)
                .build();

        Retrofit gitRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        githubUsersService = gitRetrofit.create(GithubUsersService.class);

        Retrofit overflowRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/2.2/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        overflowUsersService = overflowRetrofit.create(OverflowUsersService.class);
    }
}

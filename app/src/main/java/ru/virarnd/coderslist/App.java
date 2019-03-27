package ru.virarnd.coderslist;

import android.app.Application;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.virarnd.coderslist.models.UserRoomDatabase;
import ru.virarnd.coderslist.models.github.GithubUsersService;
import ru.virarnd.coderslist.models.overflow.OverflowUsersService;
import ru.virarnd.coderslist.presenters.UserPresenter;

import static ru.virarnd.coderslist.models.UserRoomDatabase.MIGRATION_1_2;

public class App extends Application {

    private GithubUsersService githubUsersService;
    private OverflowUsersService overflowUsersService;
    private HashMap<String, UserPresenter> userPresenters = new HashMap<>();
//    private SharedPreferences preferences;
//    private UserDatabase userDatabaseHelper;
//    private SQLiteDatabase sqLiteDatabase;

    private UserRoomDatabase roomDatabase;

    public GithubUsersService getGithubUsersService() {
        return githubUsersService;
    }

    public OverflowUsersService getOverflowUsersService() {
        return overflowUsersService;
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        userDatabaseHelper = new UserDatabase(this);
//        sqLiteDatabase = userDatabaseHelper.getWritableDatabase();

        roomDatabase = Room
                .databaseBuilder(this, UserRoomDatabase.class, "RoomDatabase")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build();

        createNetworkServices();
    }

    private void createNetworkServices() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        ChuckInterceptor chuckInterceptor = new ChuckInterceptor(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addNetworkInterceptor(chuckInterceptor)
//                .addInterceptor(new ReceivedTokenInterceptor(preferences))      // Авторизация и аутентификация
//                .addInterceptor(new AddTokenInterceptor(preferences))
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

    public void setUserPresenter(String key, UserPresenter userPresenter) {
        if (userPresenter == null) {
            userPresenters.remove(key);
        } else {
            userPresenters.put(key, userPresenter);
        }
    }

    public UserPresenter getUserPresenter(String key) {
        return userPresenters.get(key);
    }

/*
    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }
*/

    public UserRoomDatabase getRoomDatabase() {
        return roomDatabase;
    }

}

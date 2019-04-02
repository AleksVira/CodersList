package ru.virarnd.coderslist.di;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.virarnd.coderslist.models.UserRoomDatabase;

import static ru.virarnd.coderslist.models.UserRoomDatabase.MIGRATION_1_2;

@Module
public class DatabaseModule {
    @Provides
    @PerApplication
    UserRoomDatabase provideUserRoomDatabase(Context context) {
        return Room
                .databaseBuilder(context, UserRoomDatabase.class, "RoomDatabase")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build();
    }
}

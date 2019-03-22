package ru.virarnd.coderslist.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.virarnd.coderslist.models.users.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();



}

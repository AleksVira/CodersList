package ru.virarnd.coderslist.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Single;
import ru.virarnd.coderslist.models.users.User;

public class UserDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int VERSION = 2;

    private static final String USERS_TABLE_NAME = "Users";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String AVATAR = "avatar";
    private static final String USER_ID = "user_id";

    public static final String[] USER_COLUMNS = new String[]{ID, NAME, AVATAR, USER_ID};

    private static String CREATE_USER_TABLE = "create table " + USERS_TABLE_NAME
            + " ( " + ID + " integer primary key autoincrement, "
            + NAME + " TEXT NOT NULL unique, "
            + AVATAR + " TEXT NOT NULL, "
            + USER_ID + " integer NOT NULL DEFAULT 0)";

    public UserDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + USERS_TABLE_NAME + " ADD COLUMN " + USER_ID + " INTEGER NOT NULL DEFAULT 0;");
        }
    }


    public static void addUsers(List<User> users, SQLiteDatabase database) {
        for (User user : users) {
            ContentValues values = new ContentValues();
            values.put(NAME, user.getName());
            values.put(AVATAR, user.getAvatar());
            values.put(USER_ID, user.getUserId());
            database.insert(USERS_TABLE_NAME, null, values);
        }
    }

    public static void deleteUsers(SQLiteDatabase database) {
        database.delete(USERS_TABLE_NAME, null, null);
    }

    public static Single<ArrayList<User>> getAllUsers(SQLiteDatabase database) {
        return Single.create(emitter -> {
            ArrayList<User> users = new ArrayList<>();
            Cursor listCursor;
            try {
                listCursor = database.query(USERS_TABLE_NAME, USER_COLUMNS, null, null, null, null, null, null);
                listCursor.moveToFirst();
                if (!listCursor.isAfterLast()) {
                    do {
                        User user = new User(listCursor.getString(1), listCursor.getString(2), listCursor.getInt(3));
                        users.add(user);
                    } while (listCursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            emitter.onSuccess(users);
        });
    }

}

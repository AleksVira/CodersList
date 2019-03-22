package ru.virarnd.coderslist.models;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;
import ru.virarnd.coderslist.models.users.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Insert
    void addUsers(List<User> users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE userId = :id")
    User getUserById(long id);

    @Query("SELECT * FROM user WHERE name = :userName")
    User getUserById(String userName);

    @Query("DELETE FROM user")
    void deleteAll();
}

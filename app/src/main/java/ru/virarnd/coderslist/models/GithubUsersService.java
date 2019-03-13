package ru.virarnd.coderslist.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubUsersService {

    @GET("users")
    Call<List<User>> getUser(@Query("since") long id);

    @GET("users")
    Call<List<User>> getLimitedPerPageUser(@Query("since") long id, @Query("per_page") int quantity);
}

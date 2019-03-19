package ru.virarnd.coderslist.models.github;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubUsersService {

    @GET("users")
    Single<List<GitUser>> getUser(@Query("since") long id);

    @GET("users")
    Single<List<GitUser>> getLimitedPerPageUser(@Query("since") long id, @Query("per_page") int quantity);
}

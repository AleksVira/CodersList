package ru.virarnd.coderslist.models.overflow;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OverflowUsersService {

    @GET("users")
    Single<OverflowUsersUserModel.UserResponse> getLimitedPerPageUser(@Query("page") int page, @Query("site") String site, @Query("pagesize") int usersPerPage);
}

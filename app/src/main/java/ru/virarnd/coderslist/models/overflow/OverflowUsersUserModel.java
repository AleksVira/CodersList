package ru.virarnd.coderslist.models.overflow;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import ru.virarnd.coderslist.models.UserModel;
import ru.virarnd.coderslist.models.users.User;

public class OverflowUsersUserModel implements UserModel {
    public static final int USERS_PER_PAGE = 20;

    private OverflowUsersService overflowUsersService;

    public OverflowUsersUserModel(OverflowUsersService overflowUsersService) {
        this.overflowUsersService = overflowUsersService;
    }

    public OverflowUsersService getOverflowUsersService() {
        return overflowUsersService;
    }

    @Override
    public Single<List<User>> getUsers() {
        return overflowUsersService.getLimitedPerPageUser(1, "stackoverflow", USERS_PER_PAGE)
                .map(userResponse -> userResponse.getItems())
                .flatMapObservable(users -> Observable.fromIterable(users))
                .map(overflowUser -> overflowUser.mapToUser())
                .toList();
    }

    public class UserResponse {

        private ArrayList<OverflowUser> items;

        public ArrayList<OverflowUser> getItems() {
            return items;
        }

        public UserResponse(ArrayList<OverflowUser> items) {
            this.items = items;
        }
    }

    /*
    @Override
    public void getUsers(ModelResponse<List<User>> modelResponse) {
        overflowUsersService.getLimitedPerPageUser(1, "stackoverflow", USERS_PER_PAGE)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        ArrayList<User> users = new ArrayList<>();
                        for (OverflowUser overflowUser : response.body().getItems()) {
                            users.add(overflowUser.mapToUser());
                        }
                        modelResponse.onSuccess(users);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        modelResponse.onError(t);
                    }
                });
    }
*/




}
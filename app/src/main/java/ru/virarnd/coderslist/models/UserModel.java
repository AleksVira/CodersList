package ru.virarnd.coderslist.models;

import java.util.List;

import io.reactivex.Single;
import ru.virarnd.coderslist.models.users.User;

public interface UserModel {

    interface ModelResponse<T> {
        void onSuccess(T response);
        void onError(Throwable error);
    }

    Single<List<User>> getUsers();

}

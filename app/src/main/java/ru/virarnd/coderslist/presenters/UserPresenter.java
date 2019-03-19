package ru.virarnd.coderslist.presenters;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.virarnd.coderslist.models.UserModel;
import ru.virarnd.coderslist.models.users.User;

public class UserPresenter {

    public interface View {
        void onUserListLoaded(List<User> userList);
        void onError(String errorMessage);
    }

    private UserModel userModel;
    private View view;
    private Disposable disposable;
    private List<User> userList;

    public UserPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    public void attachView(View view) {
        this.view = view;
        if (userList != null) {
            view.onUserListLoaded(userList);
        } else {
            loadUsers();
        }
    }

    public void detachView() {
        this.view = null;
    }

    public void stopLoading() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
    private void loadUsers() {
        disposable = userModel.getUsers()
                .delay(3, TimeUnit.SECONDS)
                .doOnSuccess(list -> userList = list)
                .subscribeOn(Schedulers.io())
                .filter(users -> view != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> view.onUserListLoaded(users),
                        error -> view.onError("Error on network request"));
    }

}

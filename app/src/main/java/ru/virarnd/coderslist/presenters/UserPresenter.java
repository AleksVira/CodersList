package ru.virarnd.coderslist.presenters;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.virarnd.coderslist.models.UserDao;
import ru.virarnd.coderslist.models.UserDatabase;
import ru.virarnd.coderslist.models.UserModel;
import ru.virarnd.coderslist.models.UserRoomDatabase;
import ru.virarnd.coderslist.models.users.User;

public class UserPresenter {

    private static final String TAG = UserPresenter.class.getSimpleName();

    public interface View {
        void onUserListLoaded(List<User> userList);

        void onFilteredUserListLoaded(List<User> filteredList);

        void onError(String errorMessage);

        void onRemoveFilter(List<User> oldUserList);
    }

    private UserModel userModel;
    private View view;
    private Disposable disposable;
    private List<User> userList;
    private List<User> oldUserList;
    private List<User> filteredUserList;
    private SQLiteDatabase sqLiteDatabase;
    private UserRoomDatabase roomDatabase;
    private UserDao userDao;

    public UserPresenter(UserModel userModel, SQLiteDatabase sqLiteDatabase, UserRoomDatabase roomDatabase) {
        this.userModel = userModel;
        this.sqLiteDatabase = sqLiteDatabase;
        this.roomDatabase = roomDatabase;
        userDao = roomDatabase.userDao();
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
//                .delay(3, TimeUnit.SECONDS)
                .doOnSuccess(list -> userList = list)
//                .doOnSuccess(list -> UserDatabase.deleteUsers(sqLiteDatabase))
                .doOnSuccess(list -> userDao.deleteAll())
//                .doOnSuccess(list -> UserDatabase.addUsers(list, sqLiteDatabase))
                .doOnSuccess(list -> userDao.addUsers(list))
                .doOnError(throwable -> Log.d(TAG, throwable.getMessage()))
//                .onErrorResumeNext(error -> userDao.getAllUser())
//                        .flatMap(users -> users.isEmpty() ? Single.error(new RuntimeException("Error on network request")) : Single.just(users)))
//                .onErrorResumeNext(error -> UserDatabase.getAllUsers(sqLiteDatabase)
//                        .flatMap(users -> users.isEmpty() ? Single.error(new RuntimeException("Error on network request")) : Single.just(users)))
                .subscribeOn(Schedulers.io())
                .filter(users -> view != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> view.onUserListLoaded(users),
                        error -> view.onError(error.getMessage()));
    }



    public void selectFiltered(CharSequence charSequence) {
        String filter = String.valueOf(charSequence);
        filteredUserList = new ArrayList<>();
        for (User user : userList) {
            if (user.getName().toLowerCase().contains(filter.toLowerCase())) {
                filteredUserList.add(user);
            }
        }
        oldUserList = userList;
        view.onFilteredUserListLoaded(filteredUserList);
    }

    public void noFilter() {
        if (filteredUserList != null) {
            view.onRemoveFilter(oldUserList);
        }
    }


}

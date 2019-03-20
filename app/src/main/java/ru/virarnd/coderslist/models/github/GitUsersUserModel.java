package ru.virarnd.coderslist.models.github;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import ru.virarnd.coderslist.models.UserModel;
import ru.virarnd.coderslist.models.users.User;

public class GitUsersUserModel implements UserModel {

    private static final int USERS_PER_PAGE = 20;
    private static final String TAG = GitUsersUserModel.class.getSimpleName();

    private GithubUsersService githubUsersService;

    public GitUsersUserModel(GithubUsersService githubUsersService) {
        this.githubUsersService = githubUsersService;
    }

    @Override
    public Single<List<User>> getUsers() {
        return githubUsersService.getLimitedPerPageUser(0, USERS_PER_PAGE)
                .flatMapObservable(users -> Observable.fromIterable(users))
                .map(gitUser -> gitUser.mapToUser())
                .toList();


    }

    public void getMoreUsers() {

    }
}

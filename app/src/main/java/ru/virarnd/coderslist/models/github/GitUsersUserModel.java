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
/*
                .map(gitUsers -> {
                    ArrayList<User> users = new ArrayList<>();
                    for (GitUser gitUser : gitUsers) {
                        users.add(gitUser.mapToUser());
                    }
                    return users;
                });
*/
/*
        Observable<List<User>> observable = Observable.create(
                emitter -> {
                    Call call = githubUsersService.getLimitedPerPageUser(0, USERS_PER_PAGE);
                    githubUsersService.getLimitedPerPageUser(0, USERS_PER_PAGE)
                            .enqueue(new Callback<List<GitUser>>() {
                                @Override
                                public void onResponse(Call<List<GitUser>> call, Response<List<GitUser>> response) {
                                    if (response.isSuccessful()) {
                                        ArrayList<User> users = new ArrayList<>();
                                        for (GitUser gitUser : response.body()) {
                                            users.add(gitUser.mapToUser());
                                        }
                                        emitter.onNext(users);
                                    } else {
                                        Log.d(TAG, "Response is not success! Response code " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<GitUser>> call, Throwable t) {
                                    emitter.onError(t);
                                }
                            });
                    emitter.setCancellable(() -> call.cancel());

                }
        );
        return observable;
*/


    }

    public void getMoreUsers() {

    }
}

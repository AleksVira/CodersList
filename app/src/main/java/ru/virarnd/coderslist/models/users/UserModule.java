package ru.virarnd.coderslist.models.users;

import dagger.Module;
import dagger.Provides;
import ru.virarnd.coderslist.di.PerFragment;
import ru.virarnd.coderslist.models.UserRoomDatabase;
import ru.virarnd.coderslist.models.github.GitUsersUserModel;
import ru.virarnd.coderslist.models.github.GithubUsersService;
import ru.virarnd.coderslist.presenters.UserPresenter;

@Module
public class UserModule {
    @PerFragment
    @Provides
    UserPresenter provideUserPresenter(GithubUsersService githubUsersService, UserRoomDatabase roomDatabase) {
        return new UserPresenter(new GitUsersUserModel(githubUsersService), roomDatabase);

    }
}

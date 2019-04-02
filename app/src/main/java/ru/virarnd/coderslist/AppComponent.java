package ru.virarnd.coderslist;

import dagger.Component;
import ru.virarnd.coderslist.di.ContextModule;
import ru.virarnd.coderslist.di.DatabaseModule;
import ru.virarnd.coderslist.di.NetworkModule;
import ru.virarnd.coderslist.di.PerApplication;
import ru.virarnd.coderslist.models.users.UserComponent;

@Component(modules = {NetworkModule.class, ContextModule.class, DatabaseModule.class})
@PerApplication
public interface AppComponent {

    UserComponent createUserComponent();
//    void injectUsersFragment(UsersFragment usersFragment);

}

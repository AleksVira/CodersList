package ru.virarnd.coderslist.models.users;

import dagger.Subcomponent;
import ru.virarnd.coderslist.views.UsersFragment;

@Subcomponent(modules = {UserModule.class})
public interface UserComponent {

    void injectUserFragment(UsersFragment usersFragment);



}

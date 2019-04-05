package ru.virarnd.coderslist;

import android.content.Context;

import dagger.BindsInstance;
import dagger.Component;
import ru.virarnd.coderslist.di.ContextModule;
import ru.virarnd.coderslist.di.DatabaseModule;
import ru.virarnd.coderslist.di.NetworkModule;
import ru.virarnd.coderslist.di.PerApplication;
import ru.virarnd.coderslist.models.users.UserComponent;

@Component(modules = {NetworkModule.class, DatabaseModule.class})
@PerApplication
public interface AppComponent {

    Context getContext();

    UserComponent createUserComponent();

    @Component.Builder
    interface MyBuilder {

        AppComponent createComponent();

        @BindsInstance
        MyBuilder setContextBuilder(Context context);
    }
}

package ru.virarnd.coderslist.views;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import ru.virarnd.coderslist.App;
import ru.virarnd.coderslist.models.users.UserComponent;

public class FragmentViewModel extends AndroidViewModel {

    UserComponent userComponent;

    public FragmentViewModel(@NonNull Application application) {
        super(application);
        userComponent = ((App) application).getAppComponent().createUserComponent();
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userComponent = null;

    }
}


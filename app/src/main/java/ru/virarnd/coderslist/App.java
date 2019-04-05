package ru.virarnd.coderslist;

import android.app.Application;

import ru.virarnd.coderslist.di.ContextModule;

public class App extends Application {

    private AppComponent appComponent;


    public AppComponent getAppComponent() {
        return appComponent;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .setContextBuilder(this)
                .createComponent();

//        appComponent = DaggerAppComponent.builder()
//                .contextModule(new ContextModule(this))
//                .build();
    }


/*
    public void setUserPresenter(String key, UserPresenter userPresenter) {
        if (userPresenter == null) {
            userPresenters.remove(key);
        } else {
            userPresenters.put(key, userPresenter);
        }
    }

    public UserPresenter getUserPresenter(String key) {
        return userPresenters.get(key);
    }
*/


}

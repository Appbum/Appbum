package appbum.mobile.com.appbum.ui;


import android.app.Application;

import appbum.mobile.com.appbum.di.components.AppComponent;
import appbum.mobile.com.appbum.di.components.DaggerAppComponent;
import appbum.mobile.com.appbum.di.modules.AppModule;

public class AppbumApplication extends Application {

    private static AppbumApplication sInstance;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        updateDagger();
    }

    public void updateDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static AppbumApplication get() {
        return sInstance;
    }

}

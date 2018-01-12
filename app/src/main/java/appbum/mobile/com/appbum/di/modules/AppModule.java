package appbum.mobile.com.appbum.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import appbum.mobile.com.appbum.ResourceProvider;
import appbum.mobile.com.appbum.api.config.ApiConfig;
import appbum.mobile.com.appbum.managers.events.RxBus;
import appbum.mobile.com.appbum.managers.preferences.PrefsManager;
import appbum.mobile.com.appbum.ui.AppbumApplication;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final AppbumApplication app;

    public AppModule(AppbumApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context appContext() {
        return app;
    }

    @Provides
    @Singleton
    public PrefsManager preferenceManager(Context context) {
        PrefsManager.init(context);
        return PrefsManager.getInstance();
    }

    @Provides
    @Singleton
    public ApiConfig getApiConfig(Context context, PrefsManager prefsManager) {
        return new ApiConfig(prefsManager);
    }

    @Provides
    @Singleton
    public RxBus rxBus() {
        return RxBus.getInstance();
    }

    @Provides
    @Singleton
    public ResourceProvider resourceProvider(Context context) {
        return new ResourceProvider(context);
    }


}

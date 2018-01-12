package appbum.mobile.com.appbum.di.modules;

import javax.inject.Singleton;

import appbum.mobile.com.appbum.api.controllers.AppControllerApi;
import appbum.mobile.com.appbum.api.services.ApplicationApi;
import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

    @Provides
    @Singleton
    AppControllerApi getAppController(ApplicationApi applicationApi) {
        return new AppControllerApi(applicationApi);
    }
}

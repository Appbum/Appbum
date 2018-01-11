package appbum.mobile.com.appbum.di.modules;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    @Singleton
    FirebaseAuth authManager() {
        return FirebaseAuth.getInstance();
    }

}

package appbum.mobile.com.appbum.di.components

import appbum.mobile.com.appbum.ResourceProvider
import appbum.mobile.com.appbum.api.config.ApiConfig
import appbum.mobile.com.appbum.api.config.AuthenticatorService
import appbum.mobile.com.appbum.api.controllers.AppControllerApi
import appbum.mobile.com.appbum.di.modules.ApiModule
import appbum.mobile.com.appbum.di.modules.AppModule
import appbum.mobile.com.appbum.di.modules.ControllerModule
import appbum.mobile.com.appbum.di.modules.FirebaseModule
import appbum.mobile.com.appbum.managers.preferences.PrefsManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (ApiModule::class), (FirebaseModule::class), (ControllerModule::class)])
interface AppComponent {

    fun preferenceManager(): PrefsManager

    fun resourceProvider(): ResourceProvider


    /**
     * Apis
     */

    val apiConfig: ApiConfig

    fun retrofit(): Retrofit

    fun autheticaorService(): AuthenticatorService

    /**
     * Firebase
     */

    fun firebaseAuth(): FirebaseAuth

    /**
     * Controllers
     * */

    fun appController(): AppControllerApi

}

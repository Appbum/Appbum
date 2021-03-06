package appbum.mobile.com.appbum.di.modules;

import android.content.Context;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import appbum.mobile.com.appbum.api.config.ApiConfig;
import appbum.mobile.com.appbum.api.config.AuthenticatorService;
import appbum.mobile.com.appbum.api.services.ApplicationApi;
import appbum.mobile.com.appbum.managers.preferences.PrefsManager;
import appbum.mobile.com.appbum.managers.preferences.ProjectPreferences;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.net.sip.SipErrorCode.TIME_OUT;

@Module
public class ApiModule {

    private final int TIME_OUT = 20;

    @Provides
    @Singleton
    public ApplicationApi applicationApi(Retrofit retrofit) {
        return retrofit.create(ApplicationApi.class);
    }

    @Provides
    @Singleton
    public Retrofit retrofitFirebase(ApiConfig apiConfig, Gson gson, AuthenticatorService authenticator, PrefsManager prefsManager) {

        OkHttpClient.Builder httpClient = getHttpClientBuilder(apiConfig)
                .addInterceptor(addFirebaseAuthentication(prefsManager))
                .authenticator(authenticator);

        return getRetrofitBuilder(httpClient.build(), apiConfig.getFirebaseUrl(), gson).build();
    }

    @Provides
    @Singleton
    @Named("firebase-functions")
    public Retrofit retrofitFirebaseFunctions(ApiConfig apiConfig, Gson gson, AuthenticatorService authenticator, PrefsManager prefsManager) {

        OkHttpClient.Builder httpClient = getHttpClientBuilder(apiConfig)
                .addInterceptor(provideDynamicHeaderInterceptor(prefsManager))
                .authenticator(authenticator);

        return getRetrofitBuilder(httpClient.build(), apiConfig.getFuntionsUrlBase(), gson).build();
    }

    private OkHttpClient.Builder getHttpClientBuilder(ApiConfig apiConfig) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS);

        if (apiConfig.DEBUG) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            clientBuilder.addInterceptor(logging);
        }

        return clientBuilder;
    }

    private Retrofit.Builder getRetrofitBuilder(OkHttpClient httpClient, String url, Gson gson) {
        return new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
    }

    private Interceptor addFirebaseAuthentication(PrefsManager prefsManager) {

        return chain -> {

            Request.Builder newBuilder = chain.request().newBuilder();

            newBuilder.method(chain.request().method(), chain.request().body());

            String token = prefsManager.getString(ProjectPreferences.ACCESS_TOKEN);

            if (!TextUtils.isEmpty(token)) {
                newBuilder.url(chain.request().url().newBuilder().addQueryParameter(ApiConfig.PARAM_AUTHORIZATION, token)
                        .build());
            }

            return chain.proceed(newBuilder.build());
        };
    }

    private Interceptor provideDynamicHeaderInterceptor(PrefsManager prefsManager) {

        return chain -> {
            Request.Builder newBuilder;

            newBuilder = getDefaultHeaders(chain, prefsManager);

            return chain.proceed(newBuilder.build());
        };
    }

    private Request.Builder getDefaultHeaders(Interceptor.Chain chain, PrefsManager prefsManager) {
        return chain.request().newBuilder()
                .header(ApiConfig.AUTHORIZATION, ApiConfig.BEARER + prefsManager.getString(ProjectPreferences.ACCESS_TOKEN))
                .method(chain.request().method(), chain.request().body());
    }


    @Provides
    @Singleton
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    AuthenticatorService getAuthenticator(Context context, PrefsManager prefsManager, FirebaseAuth firebaseAuth) {
        return new AuthenticatorService(context, prefsManager, firebaseAuth);
    }

}

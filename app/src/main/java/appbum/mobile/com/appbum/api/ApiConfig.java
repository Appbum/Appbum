package appbum.mobile.com.appbum.api;


import appbum.mobile.com.appbum.managers.preferences.PrefsManager;

public class ApiConfig {

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "authorization";
    public static final String PARAM_AUTHORIZATION = "auth";

    public boolean DEBUG = true;

    private PrefsManager prefsManager;

    public ApiConfig(PrefsManager prefsManager) {
        this.prefsManager = prefsManager;
    }

    public String getFirebaseUrl() {
        return "https://appbum-1416a.firebaseio.com/";
    }

    public String getFuntionsUrlBase() {
        return "https://us-central1-appbum-1416a.cloudfunctions.net";
    }

}

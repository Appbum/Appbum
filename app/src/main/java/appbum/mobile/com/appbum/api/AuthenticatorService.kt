package appbum.mobile.com.appbum.api

import android.content.Context
import android.content.Intent
import appbum.mobile.com.appbum.managers.preferences.PrefsManager
import appbum.mobile.com.appbum.ui.activities.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException


class AuthenticatorService(private val context: Context, private val prefsManager: PrefsManager,
                           private val firebaseAuth: FirebaseAuth) : Authenticator {

    @Throws(IOException::class)
    override fun authenticate(route: Route, response: Response): Request? {

        prefsManager.resetPreferences()

        firebaseAuth.signOut()

        val intent = Intent(context, SplashActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        context.startActivity(intent)

        return null
    }

}
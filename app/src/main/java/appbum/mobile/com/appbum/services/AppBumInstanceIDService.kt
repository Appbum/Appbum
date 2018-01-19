package appbum.mobile.com.appbum.services

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class AppBumInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token
        Log.d("TOKEN_FIREBASE", token)
    }
}
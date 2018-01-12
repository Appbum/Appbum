package appbum.mobile.com.appbum.ui.viewModels

import appbum.mobile.com.appbum.BuildConfig
import appbum.mobile.com.appbum.api.controllers.AppControllerApi
import appbum.mobile.com.appbum.api.models.AppVersionModel
import appbum.mobile.com.appbum.ui.activities.LoginActivity
import appbum.mobile.com.appbum.ui.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SplashViewModel : BaseViewModel() {

    private val updateVersion = PublishSubject.create<Boolean>()

    @Inject
    lateinit var appControllerApi: AppControllerApi

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    init {
        getComponent().inject(this)
    }

    fun checkVersion() {
        appControllerApi.checkVersion()
                .subscribe(this::validateVersion, this::showServiceError)
    }

    private fun validateVersion(versionModel: AppVersionModel) {
        if (BuildConfig.VERSION_CODE >= versionModel.codeLastVersion && versionModel.needUpdate) {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null && !currentUser.isAnonymous) {
                startActivityEvent.onNext(MainActivity::class.java)
            } else {
                startActivityEvent.onNext(LoginActivity::class.java)
            }
        } else {
            updateVersion.onNext(true)
        }
    }

    fun updateVersionEvent(): Observable<Boolean> {
        return updateVersion.observeOn(AndroidSchedulers.mainThread())
    }

}
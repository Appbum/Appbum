package appbum.mobile.com.appbum.api.controllers

import appbum.mobile.com.appbum.api.models.AppVersionModel
import appbum.mobile.com.appbum.api.services.ApplicationApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AppControllerApi(private val applicationApi: ApplicationApi) {

    fun checkVersion(): Single<AppVersionModel> {
        return applicationApi.getAppVersion().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
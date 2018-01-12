package appbum.mobile.com.appbum.api.services

import appbum.mobile.com.appbum.api.models.AppVersionModel
import io.reactivex.Single
import retrofit2.http.GET


interface ApplicationApi {

    @GET("mobile_information/version.json")
    fun getAppVersion(): Single<AppVersionModel>
}


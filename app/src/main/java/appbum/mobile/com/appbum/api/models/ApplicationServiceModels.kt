package appbum.mobile.com.appbum.api.models

import com.google.gson.annotations.SerializedName


class AppVersionModel(@SerializedName("codeVersion") val codeLastVersion: Int, @SerializedName("needUpdate") val needUpdate: Boolean)

class ErrorServiceModel(@SerializedName("error") val error: String, @SerializedName("message") val message: String)
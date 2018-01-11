package appbum.mobile.com.appbum

import android.content.Context
import android.support.annotation.ArrayRes
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat

open class ResourceProvider(private val context: Context) {

    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    fun getString(@StringRes id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }

    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }

    fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(context, colorRes)
    }

    fun getIdentifier(name: String, defType: String): Int {
        return context.resources.getIdentifier(name, defType, context.getPackageName())
    }
}

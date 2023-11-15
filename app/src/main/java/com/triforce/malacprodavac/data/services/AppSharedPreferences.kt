package com.triforce.malacprodavac.data.services

import android.content.SharedPreferences
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun getToken(): String? =
        prefs.getString(TOKEN, null)


    fun removeToken() =
        prefs.edit()
            .remove(TOKEN)
            .apply()


    fun setRefreshToken(refreshToken: String) =
        prefs.edit()
            .putString(REFRESH_TOKEN, refreshToken)
            .apply()

    fun removeRefreshToken() =
        prefs.edit()
            .remove(REFRESH_TOKEN)
            .apply()


    companion object {
        const val REFRESH_TOKEN = "auth_token"
        const val TOKEN = "auth_token"
        const val SHARED_PREFS = "APP_SHARED_PREFS"
    }
}
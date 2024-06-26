package com.triforce.malacprodavac.data.remote.auth.interceptors

import com.triforce.malacprodavac.data.services.SessionManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthInterceptorImpl @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authToken = sessionManager.getAccessToken()
        val response = chain.proceed(newRequestWithAuthToken(authToken, request))

        if (
            (request.url.pathSegments.contains("login") && response.isSuccessful)
            ||
            (request.url.pathSegments.contains("me") && response.isSuccessful)
        ) {
            val cookie = response.header("Set-Cookie", null)
            val jwt = cookie?.split(";")?.first()
            refreshToken(jwt)
        } else if ((request.url.pathSegments.contains("logout") && response.code == HttpURLConnection.HTTP_NO_CONTENT)
            || response.code == HttpURLConnection.HTTP_UNAUTHORIZED
        ) {
            sessionManager.logout()
        }

        return response
    }


    private fun newRequestWithAuthToken(authToken: String?, request: Request): Request =
        request.newBuilder()
            .header("Cookie", "$authToken").build()

    private fun refreshToken(refreshToken: String?): String? {
        synchronized(this) {
            refreshToken?.let {
                return sessionManager.refreshToken(it)
            } ?: return null
        }
    }
}
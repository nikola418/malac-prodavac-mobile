package com.triforce.malacprodavac.data.remote.auth

import com.triforce.malacprodavac.data.remote.auth.dto.LoginDto
import com.triforce.malacprodavac.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/${BASE_URL}/login")
    suspend fun login(
        @Body loginRequest: LoginDto
    ): User

    companion object {
        const val BASE_URL = "auth"
    }
}
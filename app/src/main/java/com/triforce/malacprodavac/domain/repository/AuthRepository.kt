package com.triforce.malacprodavac.domain.repository

import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<User>>

    suspend fun me(): Flow<Resource<User>>

    suspend fun logout(): Flow<Resource<Unit>>
}
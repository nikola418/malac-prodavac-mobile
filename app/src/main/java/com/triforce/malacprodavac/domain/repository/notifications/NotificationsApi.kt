package com.triforce.malacprodavac.domain.repository.notifications

import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {
    suspend fun subscribe(): Flow<Resource<Unit>>
}
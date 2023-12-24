package com.triforce.malacprodavac.domain.repository

import com.triforce.malacprodavac.data.remote.orders.dto.CreateSchedulePickupDto
import com.triforce.malacprodavac.domain.model.SchedulePickup
import com.triforce.malacprodavac.domain.model.UpdateScheduledPickup
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ScheduledPickupRepository {
    suspend fun getScheduledPickups(
        id: Int,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<SchedulePickup>>>

    suspend fun getScheduledPickup(
        id: Int,
        scheduledPickupId: Int,
        fetchFromRemote: Boolean,
    ): Flow<Resource<SchedulePickup>>

    suspend fun insertScheduledPickup(
        id: Int,
        createSchedulePickup: CreateSchedulePickupDto
    ): Flow<Resource<SchedulePickup>>

    suspend fun updateScheduledPickup(
        id: Int,
        scheduledPickupId: Int,
        updateSchedulePickup: UpdateScheduledPickup
    ): Flow<Resource<SchedulePickup>>
}
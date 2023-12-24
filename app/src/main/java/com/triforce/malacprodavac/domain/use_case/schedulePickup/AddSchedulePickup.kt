package com.triforce.malacprodavac.domain.use_case.schedulePickup

import com.triforce.malacprodavac.data.remote.orders.dto.CreateSchedulePickupDto
import com.triforce.malacprodavac.domain.repository.ScheduledPickupRepository

class AddSchedulePickup(
    private val repository: ScheduledPickupRepository
) {

    suspend operator fun invoke(id: Int, createSchedulePickup: CreateSchedulePickupDto) {

        repository.insertScheduledPickup(id, createSchedulePickup)
    }
}
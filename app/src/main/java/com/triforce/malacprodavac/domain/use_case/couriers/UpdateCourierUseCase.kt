package com.triforce.malacprodavac.domain.use_case.couriers

import com.triforce.malacprodavac.domain.model.couriers.UpdateCourier
import com.triforce.malacprodavac.domain.repository.CourierRepository

data class UpdateCourierUseCase(val repository: CourierRepository) {
    suspend operator fun invoke(courierId: Int, dto: UpdateCourier) =
        repository.updateCourier(courierId, dto)
}

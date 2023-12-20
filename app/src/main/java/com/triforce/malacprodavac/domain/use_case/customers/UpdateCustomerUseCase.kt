package com.triforce.malacprodavac.domain.use_case.customers

import com.triforce.malacprodavac.domain.model.customers.UpdateCustomer
import com.triforce.malacprodavac.domain.repository.CustomerRepository

data class UpdateCustomerUseCase(val repository: CustomerRepository) {
    suspend operator fun invoke(customerId: Int, dto: UpdateCustomer) =
        repository.updateCustomer(customerId, dto)
}

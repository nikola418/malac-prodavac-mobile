package com.triforce.malacprodavac.domain.use_case.shops

import com.triforce.malacprodavac.domain.model.shops.UpdateShop
import com.triforce.malacprodavac.domain.repository.ShopRepository

data class UpdateShopUseCase(val repository: ShopRepository) {
    suspend operator fun invoke(shopId: Int, dto: UpdateShop) =
        repository.updateShop(shopId, dto)
}

package com.triforce.malacprodavac.domain.repository

import com.triforce.malacprodavac.data.remote.customers.dto.CreateFavoriteProductDto
import com.triforce.malacprodavac.data.repository.customers.favoriteShops.dto.CreateFavoriteShopDto
import com.triforce.malacprodavac.domain.model.CreateCustomer
import com.triforce.malacprodavac.domain.model.Customer
import com.triforce.malacprodavac.domain.model.customers.FavoriteProduct
import com.triforce.malacprodavac.domain.model.customers.FavoriteShop
import com.triforce.malacprodavac.domain.model.customers.UpdateCustomer
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun registerCustomer(
        createCustomer: CreateCustomer
    ): Flow<Resource<Customer>>

    suspend fun getFavoriteProducts(
        customerId: Int,
        fetchFromRemote: Boolean,
        queryMap: MutableMap<String, String>
    ): Flow<Resource<List<FavoriteProduct>>>

    suspend fun updateCustomer(customerId: Int, dto: UpdateCustomer): Flow<Resource<Customer>>

    suspend fun insertFavoriteProduct(
        customerId: Int,
        createFavoriteProductDto: CreateFavoriteProductDto
    ): Flow<Resource<FavoriteProduct>>

    suspend fun deleteFavoriteProduct(
        id: Int,
        favoriteProductId: Int
    ): Flow<Resource<FavoriteProduct>>

    suspend fun getFavoriteShops(
        customerId: Int,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<FavoriteShop>>>

    suspend fun insertFavoriteShop(
        customerId: Int,
        createFavoriteShopDto: CreateFavoriteShopDto
    ): Flow<Resource<FavoriteShop>>

    suspend fun deleteFavoriteShop(id: Int, favoriteShopId: Int): Flow<Resource<FavoriteShop>>
}

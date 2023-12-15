package com.triforce.malacprodavac.domain.repository.products

import com.triforce.malacprodavac.domain.model.pagination.PaginationResult
import com.triforce.malacprodavac.domain.model.products.CreateProductDto
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.domain.model.products.UpdateProductDto
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(
        categoryId: Int,
        fetchFromRemote: Boolean,
        queryMap: MutableMap<String, String>
    ): Flow<Resource<PaginationResult<Product>>>

    suspend fun getProduct(
        id: Int,
        fetchFromRemote: Boolean,
    ): Flow<Resource<Product>>

    suspend fun deleteProduct(id: Int): Flow<Resource<Product>>
    suspend fun insertProduct(createProductDto: CreateProductDto): Flow<Resource<Product>>

    suspend fun updateProduct(id: Int, updateProduct: UpdateProductDto): Flow<Resource<Product>>
}
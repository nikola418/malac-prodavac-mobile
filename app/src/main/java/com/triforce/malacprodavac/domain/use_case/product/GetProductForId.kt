package com.triforce.malacprodavac.domain.use_case.product

import com.triforce.malacprodavac.domain.model.Product
import com.triforce.malacprodavac.domain.repository.ProductRepository
import com.triforce.malacprodavac.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.QueryMap

class GetProductForId(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        categoryId: Int,
        fetchFromRemote: Boolean,
        @QueryMap() query: MutableMap<String, String>
    ): Flow<Resource<List<Product>>> {
        return repository.getProducts(categoryId, fetchFromRemote, query)
    }
}
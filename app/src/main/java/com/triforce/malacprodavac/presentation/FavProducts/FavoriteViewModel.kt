package com.triforce.malacprodavac.presentation.FavProducts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.data.remote.customers.dto.CreateFavoriteProductDto
import com.triforce.malacprodavac.domain.util.filter.Filter
import com.triforce.malacprodavac.domain.util.filter.FilterBuilder
import com.triforce.malacprodavac.domain.util.filter.FilterOperation
import com.triforce.malacprodavac.domain.util.filter.SingleFilter
import com.triforce.malacprodavac.domain.model.customers.FavoriteProduct
import com.triforce.malacprodavac.domain.repository.CustomerRepository
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: CustomerRepository,
    private val profile: Profile
) : ViewModel() {

    var state by mutableStateOf(FavoriteState())

    init {
        me()
    }

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.AddFavProduct -> addFavProduct(
                state.customerId!!,
                CreateFavoriteProductDto(productId = event.productId)
            )

            is FavoriteEvent.GetFavProducts -> getFavProducts(state.customerId!!)
            is FavoriteEvent.DeleteFavProduct -> deleteFavProduct(
                state.customerId!!,
                event.productId
            )
        }
    }

    private fun getFavProducts(
        userId: Int
    ) {
        viewModelScope.launch {
            val query = FilterBuilder.buildFilterQueryMap(
                Filter(
                    filter = listOf(),
                    order = null,
                    limit = null,
                    offset = null
                )
            )

            repository.getFavoriteProducts(userId, true, query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data is List<FavoriteProduct>) {
                            state = state.copy(favProducts = result.data)
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private suspend fun getFavProduct(userId: Int, productId: Int): FavoriteProduct? {
        var favoriteProduct: FavoriteProduct? = null

        viewModelScope.launch {
            val query = FilterBuilder.buildFilterQueryMap(
                Filter(
                    filter = listOf(
                        SingleFilter("productId", FilterOperation.Eq, productId)
                    ),
                    order = null,
                    limit = null,
                    offset = null
                )
            )

            repository.getFavoriteProducts(userId, true, query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data is List<FavoriteProduct> && result.data.isNotEmpty()) {
                            favoriteProduct = result.data.first()
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }.join()

        return favoriteProduct
    }

    private fun addFavProduct(
        userId: Int,
        createFavoriteProductDto: CreateFavoriteProductDto
    ) {
        viewModelScope.launch {
            repository.insertFavoriteProduct(userId, createFavoriteProductDto).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data is FavoriteProduct) {
                            state = state.copy(favProduct = result.data)
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun deleteFavProduct(
        userId: Int,
        productId: Int
    ) {
        viewModelScope.launch {
            val favProduct = getFavProduct(userId, productId)

            favProduct?.let { product ->
                repository.deleteFavoriteProduct(userId, product.id)
                    .collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                if (result.data is FavoriteProduct) {
                                    state = state.copy(favProduct = result.data)
                                    updateFavoriteProductsAfterDeletion(productId)
                                }
                            }

                            is Resource.Error -> handleError()
                            is Resource.Loading -> handleLoading(result.isLoading)
                        }
                    }
            }
        }
    }

    private fun updateFavoriteProductsAfterDeletion(productId: Int) {
        val updatedList = state.favProducts.toMutableList()
        updatedList.removeIf { it.product?.id == productId }
        state = state.copy(favProducts = updatedList)
    }

    private fun me() {
        viewModelScope.launch {
            profile.getMe().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data != null) {
                            state = state.copy(
                                customerId = result.data.customer!!.id
                            )
                            getFavProducts(state.customerId!!)
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun handleError() {
    }

    private fun handleLoading(isLoading: Boolean) {
        state = state.copy(isLoading = isLoading)
    }
}
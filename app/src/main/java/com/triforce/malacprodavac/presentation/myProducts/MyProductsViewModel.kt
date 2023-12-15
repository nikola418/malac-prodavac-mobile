package com.triforce.malacprodavac.presentation.myProducts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.domain.repository.products.ProductRepository
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.domain.util.filter.Filter
import com.triforce.malacprodavac.domain.util.filter.FilterBuilder
import com.triforce.malacprodavac.domain.util.filter.FilterOperation
import com.triforce.malacprodavac.domain.util.filter.FilterOrder
import com.triforce.malacprodavac.domain.util.filter.SingleFilter
import com.triforce.malacprodavac.domain.util.filter.SingleOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProductsViewModel @Inject constructor(

    private val profile: Profile,
    private val repositoryProduct: ProductRepository

) : ViewModel() {

    var state by mutableStateOf(MyProductsState())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val debouncePeriod = 500L;
    private var searchJob: Job? = null

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            state.user?.shop?.id?.let { getProducts(it, text) }
        }
    }

    init {
        handleLoading(false)
        me()
        getToken()
    }

    private fun getToken() {
        profile.getToken().let {
            state = state.copy(token = it)
        }
    }

    fun onEvent(event: MyProductsEvent) {
        when (event) {
            is MyProductsEvent.OrderBy -> {
                state.user?.shop?.id?.let {
                    getProducts(shopId = it, searchText = searchText.value, orderId = event.order)
                }
            }
        }
    }

    private fun me() {
        viewModelScope.launch {
            profile.getMe().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            user = result.data,
                            profileImageUrl = "http://softeng.pmf.kg.ac.rs:10010/users/${result.data?.profilePicture?.userId}/medias/${result.data?.profilePicture?.id}",
                            profileImageKey = result.data?.profilePicture?.key
                        )

                        state.user?.let {
                            getProducts(shopId = it.shop!!.id)
                        }
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun getProducts(shopId: Int, searchText: String = "", orderId: Int = -1) {
        viewModelScope.launch {

            val query = FilterBuilder.buildFilterQueryMap(
                Filter(
                    filter = listOf(
                        SingleFilter(
                            "shopId",
                            FilterOperation.Eq,
                            shopId
                        ),
                        SingleFilter(
                            "title",
                            FilterOperation.IContains,
                            searchText
                        )
                    ),
                    order = if (orderId == -1) null else listOf(
                        SingleOrder(
                            "price",
                            if (orderId == 1) FilterOrder.Asc else FilterOrder.Desc
                        )
                    ),
                    limit = null,
                    offset = null
                )
            )

            repositoryProduct.getProducts(shopId, true, query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data is List<Product>) {
                            state = state.copy(products = result.data, isLoading = false)
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
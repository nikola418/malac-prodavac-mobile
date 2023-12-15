package com.triforce.malacprodavac.presentation.myProducts

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.domain.repository.ShopRepository
import com.triforce.malacprodavac.domain.repository.products.ProductRepository
import com.triforce.malacprodavac.domain.repository.users.UserRepository
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.domain.util.compressedFileFromUri
import com.triforce.malacprodavac.domain.util.filter.Filter
import com.triforce.malacprodavac.domain.util.filter.FilterBuilder
import com.triforce.malacprodavac.domain.util.filter.FilterOperation
import com.triforce.malacprodavac.domain.util.filter.SingleFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProductsViewModel @Inject constructor(

    private val profile: Profile,
    private val repositoryShop: ShopRepository,
    private val repositoryUser: UserRepository,
    private val repositoryProduct: ProductRepository,

    savedStateHandle: SavedStateHandle

) : ViewModel() {
    var state by mutableStateOf(MyProductsState())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    var currentShopId: Int? = null
    fun onSearchTextChange(text: String) {
        _searchText.value = text
        currentShopId?.let {
            getProducts(
                fetchFromRemote = true,
                id = currentShopId!!,
                filterTag = "shopId",
                searchText = text
            )
        }
    }

    init {
        me()
        getToken()
    }

    private fun getToken() {
        profile.getToken().let {
            Log.d("TOKEN", it.toString())
            state = state.copy(token = it)
        }
    }


    fun onEvent(event: MyProductsEvent) {
        when (event) {
            MyProductsEvent.Refresh -> {
                me()
            }
        }
    }

    private fun setProfilePicture(context: Context, uri: Uri) {
        viewModelScope.launch {
            val file = compressedFileFromUri(context, uri)
            profile.setProfilePicture(state.currentUser!!.id, file)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }

                        is Resource.Success -> {
                            state =
                                state.copy(
                                    profileImageUrl = "http://softeng.pmf.kg.ac.rs:10010/users/${result.data?.userId}/medias/${result.data?.id}",
                                    profileImageKey = result.data?.key
                                )
                        }
                    }
                }
        }
    }

    private fun me() {
        viewModelScope.launch {
            profile.getMe().collect { result ->
                when (result) {
                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            currentUser = result.data,
                            profileImageUrl = "http://softeng.pmf.kg.ac.rs:10010/users/${result.data?.profilePicture?.userId}/medias/${result.data?.profilePicture?.id}",
                            profileImageKey = result.data?.profilePicture?.key
                        )

                        state.currentUser?.let {
                            getShop(shopId = it.shop!!.id)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getProducts(
        fetchFromRemote: Boolean,
        filterTag: String = "",
        id: Int,
        searchText: String = ""
    ) {

        viewModelScope.launch {

            val query = FilterBuilder.buildFilterQueryMap(
                Filter(
                    filter = listOf(
                        SingleFilter(
                            filterTag,
                            FilterOperation.Eq,
                            id
                        ),
                        SingleFilter(
                            "title",
                            FilterOperation.IContains,
                            searchText
                        )
                    ), order = null, limit = null, offset = null
                )
            )

            repositoryProduct.getProducts(id, fetchFromRemote, query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            products = result.data?.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        Unit
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    private fun getShop(shopId: Int) {
        viewModelScope.launch {
            repositoryShop.getShop(fetchFromRemote = true, id = shopId)
                .collectLatest { result ->
                    when (result) {

                        is Resource.Success -> {
                            if (result.data is Shop) {
                                state = state.copy(
                                    currentShop = result.data
                                )

                                getProducts(
                                    fetchFromRemote = true,
                                    filterTag = "shopId",
                                    id = state.currentShop!!.id
                                )
                            }
                        }

                        is Resource.Error -> {
                            Unit
                        }

                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }
}
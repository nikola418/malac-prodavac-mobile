package com.triforce.malacprodavac.presentation.store.category

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.repository.products.ProductRepository
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import com.triforce.malacprodavac.domain.util.filter.Filter
import com.triforce.malacprodavac.domain.util.filter.FilterBuilder
import com.triforce.malacprodavac.domain.util.filter.FilterOperation
import com.triforce.malacprodavac.domain.util.filter.FilterOrder
import com.triforce.malacprodavac.domain.util.filter.SingleFilter
import com.triforce.malacprodavac.domain.util.filter.SingleOrder
import com.triforce.malacprodavac.domain.util.helpers.calculateLimitAndOffset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val profile: Profile,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(CategoryState())

    private val debouncePeriod = 500L
    private var searchJob: Job? = null

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _categoryTitle = mutableStateOf(
        CategoryState(
            title = "Category title..."
        )
    )
    val categoryTitle: State<CategoryState> = _categoryTitle

    var currentCategoryId: Int? = null

    init {
        savedStateHandle.get<Int>("categoryId")?.let { categoryId ->
            currentCategoryId = categoryId
            me()
            getToken()
        }
        savedStateHandle.get<String>("title")?.let { title ->
            _categoryTitle.value = categoryTitle.value.copy(title = title)
        }
    }

    private fun getToken() {
        profile.getToken().let {
            state = state.copy(token = it)
        }
    }

    private fun me() {
        viewModelScope.launch {
            profile.getMe().collect { result ->
                when (result) {
                    is Resource.Error -> {}

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            user = result.data,
                            profileImageUrl = "http://softeng.pmf.kg.ac.rs:10010/users/${result.data?.profilePicture?.userId}/medias/${result.data?.profilePicture?.id}",
                            profileImageKey = result.data?.profilePicture?.key
                        )

                        callGetProducts()
                    }
                }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            callGetProducts()
        }
    }

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.OrderBy -> {
                callGetProducts(event.order)
            }
        }
    }

    private fun getProducts(
        myShopId: Int = -1,
        categoryId: Int,
        searchText: String = "",
        orderId: Int = -1,
        page: Int = 1,
        perPage: Int = 20
    ) {
        var filters = listOf(
            SingleFilter(
                "category.parentCategoryId",
                FilterOperation.Eq,
                categoryId.toString()
            )
        )

        if (searchText != "")
            filters +=
                SingleFilter(
                    "title",
                    FilterOperation.IContains,
                    searchText
                )

        if (myShopId != -1)
            filters += SingleFilter(
                "shopId",
                FilterOperation.Ne,
                myShopId.toString()
            )
        val (limit, offset) = calculateLimitAndOffset(page, perPage)
        viewModelScope.launch {
            val query = FilterBuilder.buildFilterQueryMap(
                Filter(
                    filter = filters,
                    order = if (orderId == -1) null else listOf(
                        SingleOrder(
                            "price",
                            if (orderId == 1) FilterOrder.Asc else FilterOrder.Desc
                        )
                    ),
                    offset,
                    limit
                )
            )
            repository.getProducts(categoryId, true, query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data != null)
                            state = state.copy(
                                products = state.products + result.data.data,
                                currentPage = result.data.meta.currentPage,
                                isLastPage = result.data.meta.isLastPage
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

    fun loadNextPage(page: Int = 1, perPage: Int = 20) {
        getProducts(categoryId = currentCategoryId!!, page = page + 1, perPage = perPage)
    }

    private fun callGetProducts(orderId: Int = -1) {
        currentCategoryId?.let { categoryId ->
            state.user?.let { user ->
                if (user.roles.contains("Shop")) {
                    user.shop?.let { shop ->
                        getProducts(
                            myShopId = shop.id,
                            categoryId = categoryId,
                            searchText = searchText.value,
                            orderId = orderId
                        )
                    }
                } else {
                    getProducts(
                        categoryId = categoryId,
                        searchText = searchText.value,
                        orderId = orderId
                    )
                }
            }
        }
    }
}
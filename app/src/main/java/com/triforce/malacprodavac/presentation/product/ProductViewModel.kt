package com.triforce.malacprodavac.presentation.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triforce.malacprodavac.domain.model.products.reviews.CreateReviewDto
import com.triforce.malacprodavac.domain.model.products.reviews.reviewReplies.CreateReviewReplyDto
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.domain.repository.ShopRepository
import com.triforce.malacprodavac.domain.repository.products.ProductRepository
import com.triforce.malacprodavac.domain.use_case.product.replies.ReviewUseCase
import com.triforce.malacprodavac.domain.use_case.product.replies.replies.ReviewReplyUseCase
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val profile: Profile,
    private val repository: ProductRepository,
    private val reviewUseCase: ReviewUseCase,
    private val reviewReplyUseCase: ReviewReplyUseCase,
    private val repositoryShop: ShopRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(ProductState())

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.buyProduct -> state = state.copy(isBuyed = true)
            is ProductEvent.ToggleFavouriteProduct -> state = state.copy(isBuyed = true)
            is ProductEvent.CreateReview -> createReview(event.text, event.rating)
            is ProductEvent.CreateReplyReview -> createReplyReview(event.text, event.reviewId)
            is ProductEvent.FetchReviewReplies -> getReplyReviews(event.productId, event.reviewId)
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

                    }
                }
            }
        }
    }

    init {
        savedStateHandle.get<Int>("productId")?.let { productId ->
            me()
            getToken()

            getProduct(productId)
            getReviews(productId)
        }
    }

    private fun getReviews(productId: Int) {
        viewModelScope.launch {
            reviewUseCase.getReviews(productId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(reviews = result.data)
                    }

                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    public fun getReplyReviews(productId: Int, reviewId: Int) {
        viewModelScope.launch {
            reviewReplyUseCase.getReviewReplies(productId, reviewId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(replyReviews = result.data)
                    }


                    is Resource.Error -> handleError()
                    is Resource.Loading -> handleLoading(result.isLoading)
                }
            }
        }
    }

    private fun createReview(text: String, rating: Int) {
        viewModelScope.launch {
            reviewUseCase.createReview.invoke(state.product!!.id, CreateReviewDto(text, rating))
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(
                                    reviews = listOf(*state.reviews!!.toTypedArray(), it)
                                )
                            }
                        }

                        is Resource.Error -> {
                            state = state.copy(createReviewError = result.message)
                        }

                        is Resource.Loading -> handleLoading(result.isLoading)
                    }
                }
        }
    }

    private fun createReplyReview(text: String, reviewId: Int) {
        viewModelScope.launch {
            reviewReplyUseCase.createReviewReply.invoke(
                state.product!!.id,
                reviewId,
                CreateReviewReplyDto(text)
            )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(
                                    replyReviews = listOf(*state.replyReviews!!.toTypedArray(), it)
                                )
                            }
                        }

                        is Resource.Error -> {
                            state = state.copy(createReplyReviewError = result.message)
                        }

                        is Resource.Loading -> handleLoading(result.isLoading)
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
                                state = state.copy(shop = result.data, isLoading = false)
                            }
                        }

                        is Resource.Error -> handleError()
                        is Resource.Loading -> handleLoading(result.isLoading)
                    }
                }
        }
    }

    private fun getProduct(productId: Int) {
        viewModelScope.launch {
            repository.getProduct(productId, true).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            state =
                                state.copy(
                                    product = it,
                                    thumbnailUrl = if (it.productMedia != null) "http://softeng.pmf.kg.ac.rs:10010/products/${it.productMedia.productId}/medias/${it.productMedia.id}" else null,
                                    thumbnailKey = it.productMedia?.key,
                                )
                        }
                        state.product?.let {
                            getShop(it.shopId)
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
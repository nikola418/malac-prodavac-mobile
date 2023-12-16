package com.triforce.malacprodavac.presentation.product

import com.triforce.malacprodavac.domain.model.User
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.domain.model.products.reviews.Review
import com.triforce.malacprodavac.domain.model.products.reviews.reviewReplies.ReviewReply
import com.triforce.malacprodavac.domain.model.shops.Shop

data class ProductState(
    val product: Product? = null,
    val reviews: List<Review>? = emptyList(),
    val replyReviews: List<ReviewReply>? = emptyList(),
    val thumbnailUrl: String? = null,
    val thumbnailKey: String? = null,
    val createReviewError: String? = null,
    val createReplyReviewError: String? = null,
    val isLoading: Boolean = false,
    val isBuyed: Boolean = false,


    val user: User? = null,
    val token: String? = null,
    val shop: Shop? = null,
    val profileImageUrl: String? = null,
    val profileImageKey: String? = null,
)
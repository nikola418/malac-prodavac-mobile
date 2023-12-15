package com.triforce.malacprodavac.presentation.product

sealed class ProductEvent {
    object ToggleFavouriteProduct : ProductEvent()
    object buyProduct : ProductEvent()
    data class CreateReview(val text: String, val rating: Int) : ProductEvent()
    data class CreateReplyReview(val text: String) : ProductEvent()
}
package com.triforce.malacprodavac.data.remote.products.reviews.replies

import com.triforce.malacprodavac.data.remote.products.reviews.ReviewsApi
import com.triforce.malacprodavac.domain.model.pagination.PaginationResult
import com.triforce.malacprodavac.domain.model.products.reviews.reviewReplies.CreateReviewReplyDto
import com.triforce.malacprodavac.domain.model.products.reviews.reviewReplies.ReviewReply
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewRepliesApi {
    @POST(ROUTE)
    suspend fun createReviewReply(
        @Path("productId") productId: Int,
        @Path("reviewId") reviewId: Int,
        @Body createReviewReply: CreateReviewReplyDto
    ): ReviewReply

    @GET(ROUTE)
    suspend fun getReviewReplies(
        @Path("productId") productId: Int,
        @Path("reviewId") reviewId: Int,
    ): PaginationResult<ReviewReply>

    @GET("${ROUTE}/{replyId}")
    suspend fun getReviewReply(
        @Path("productId") productId: Int,
        @Path("reviewId") reviewId: Int,
        @Path("replyId") replyId: Int
    ): ReviewReply

    companion object {
        const val ROUTE = "${ReviewsApi.ROUTE}/{reviewId}/replies"
    }
}
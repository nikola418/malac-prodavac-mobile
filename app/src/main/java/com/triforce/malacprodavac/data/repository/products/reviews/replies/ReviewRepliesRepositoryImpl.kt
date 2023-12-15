package com.triforce.malacprodavac.data.repository.products.reviews.replies

import com.triforce.malacprodavac.data.local.MalacProdavacDatabase
import com.triforce.malacprodavac.data.remote.products.reviews.replies.ReviewRepliesApi
import com.triforce.malacprodavac.data.services.SessionManager
import com.triforce.malacprodavac.domain.model.products.reviews.reviewReplies.CreateReviewReplyDto
import com.triforce.malacprodavac.domain.model.products.reviews.reviewReplies.ReviewReply
import com.triforce.malacprodavac.domain.repository.products.reviews.replies.ReviewRepliesRepository
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ReviewRepliesRepositoryImpl @Inject constructor(
    val api: ReviewRepliesApi,
    val db: MalacProdavacDatabase,
    val sessionManager: SessionManager
) : ReviewRepliesRepository {
    override suspend fun createReviewReply(
        productId: Int,
        reviewId: Int,
        createReviewReply: CreateReviewReplyDto
    ): Flow<Resource<ReviewReply>> {
        return flow {
            emit(Resource.Loading(true))

            val replyReview = try {
                api.createReviewReply(productId, reviewId, createReviewReply)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't find user."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == HttpURLConnection.HTTP_CONFLICT)
                    emit(Resource.Error("Ne možete ostaviti više od jedne recenzije!"))
                else if (e.code() == HttpURLConnection.HTTP_BAD_REQUEST)
                    emit(Resource.Error("Morate popuniti sva polja!"))
                else if (e.code() == HttpURLConnection.HTTP_FORBIDDEN)
                    emit(Resource.Error("Morate kupiti proizvod da biste postavili recenziju!"))
                else
                    emit(Resource.Error("Postavljanje recenzije neuspešno!"))
                null
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't find user."))
                null
            }

            replyReview?.let {
                emit(Resource.Success(data = replyReview))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getReviewReplies(
        productId: Int,
        reviewId: Int
    ): Flow<Resource<List<ReviewReply>>> {
        return flow {
            emit(Resource.Loading(true))

            val replyReviews = try {
                api.getReviewReplies(productId, reviewId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't find user."))
                null
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't find user."))
                null
            }

            replyReviews?.let {
                emit(Resource.Success(data = replyReviews.data))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getReviewReply(
        productId: Int,
        reviewId: Int,
        replyId: Int
    ): Flow<Resource<ReviewReply>> {
        TODO("Not yet implemented")
    }
}
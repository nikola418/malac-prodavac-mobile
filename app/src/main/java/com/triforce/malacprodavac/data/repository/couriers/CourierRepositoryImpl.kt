package com.triforce.malacprodavac.data.repository.couriers

import com.triforce.malacprodavac.data.local.MalacProdavacDatabase
import com.triforce.malacprodavac.data.remote.couriers.CouriersApi
import com.triforce.malacprodavac.data.remote.couriers.dto.CreateCourierDto
import com.triforce.malacprodavac.domain.model.Courier
import com.triforce.malacprodavac.domain.model.CreateCourier
import com.triforce.malacprodavac.domain.model.couriers.UpdateCourier
import com.triforce.malacprodavac.domain.model.pagination.PaginationResult
import com.triforce.malacprodavac.domain.repository.CourierRepository
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourierRepositoryImpl @Inject constructor(
    private val api: CouriersApi,
    private val db: MalacProdavacDatabase
) : CourierRepository {
    override suspend fun registerCourier(
        createCourier: CreateCourier
    ): Flow<Resource<Courier>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val courier = try {
                api.registerCouriers(
                    CreateCourierDto(createCourier.user, createCourier.pricePerKilometer)
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't register user."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == HttpURLConnection.HTTP_CONFLICT) {
                    emit(Resource.Error("Email je zauzet!"))
                } else {
                    emit(Resource.Error("Nije moguće napraviti nalog!"))
                }
                null
            }
            courier?.let {
//                authenticateUser(it)
//                dao.insertUser(listOf(it.user.toUserEntity()))
                emit(Resource.Success(data = it))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }


    override suspend fun getCouriers(
        filters: MutableMap<String, String>
    ): Flow<Resource<PaginationResult<Courier>>> {
        return flow {

            emit(Resource.Loading(true))
            val result = try {
                api.getCouriers(filters)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load couriers"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load couriers data"))
                null
            }

            result?.let {
                emit(Resource.Success(result))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCourier(courierId: Int): Flow<Resource<Courier>> {
        return flow {
            emit(Resource.Loading(true))
            val result = try {
                api.getCourier(courierId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load couriers"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load couriers data"))
                null
            }

            result?.let {
                emit(Resource.Success(result))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateCourier(
        courierId: Int,
        dto: UpdateCourier
    ): Flow<Resource<Courier>> {
        return flow {
            emit(Resource.Loading(true))

            val result = try {
                api.updateCourier(courierId, dto)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update courier"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update courier data"))
                null
            }

            result?.let {
                emit(Resource.Success(it))
            }

            emit(Resource.Loading(false))
        }
    }

}
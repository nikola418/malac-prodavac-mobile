package com.triforce.malacprodavac.data.repository.orders

import android.util.Log
import com.triforce.malacprodavac.data.local.MalacProdavacDatabase
import com.triforce.malacprodavac.data.mappers.toOrder
import com.triforce.malacprodavac.data.remote.orders.OrderApi
import com.triforce.malacprodavac.data.remote.orders.dto.CreateOrderDto
import com.triforce.malacprodavac.data.remote.orders.dto.UpdateOrderDto
import com.triforce.malacprodavac.domain.model.CreateOrder
import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.domain.model.UpdateOrder
import com.triforce.malacprodavac.domain.repository.OrderRepository
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderApi,
    private val db: MalacProdavacDatabase
) : OrderRepository {

    private val dao = db.orderDao

    override suspend fun getOrders(
        fetchFromRemote: Boolean,
        //queryMap: MutableMap<String, String>
    ): Flow<Resource<List<Order>>> {
        return flow {

            emit(Resource.Loading(isLoading = true))

            val localOrders = dao.getAllOrders()

            if (localOrders.isNotEmpty()) {
                emit(Resource.Success(data = localOrders.map { it.toOrder() }))
            }


            val isDbEmpty = localOrders.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteOrders = try {
                api.getOrders()
            } catch (e: IOException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load order"))
                null

            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load order data"))
                null

            }

            remoteOrders?.let {
                emit(Resource.Success(remoteOrders.data.map { jt -> jt }))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getOrder(id: Int, fetchFromRemote: Boolean): Flow<Resource<Order>> {
        return flow {

            emit(Resource.Loading(isLoading = true))

            val localOrders = dao.getOrderForId(id)

            if (localOrders.isNotEmpty()) {
                emit(Resource.Success(data = localOrders.first().toOrder()))
            }


            val isDbEmpty = localOrders.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteOrders = try {
                api.getOrder(id)
            } catch (e: IOException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load order"))
                null

            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load order data"))
                null

            }

            remoteOrders?.let {

                Log.d("PRODUCTS:", it.toString())
                emit(Resource.Success(remoteOrders))

            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun deleteOrder(id: Int): Flow<Resource<Order>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val deleteOrder = try {
                api.delete(id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't delete Order"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't delete Order"))
                null
            }
            deleteOrder?.let {
                emit(Resource.Success(it))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun insertOrder(createOrder: CreateOrder): Flow<Resource<Order>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val updateOrder = try {
                api.create(createOrder as CreateOrderDto)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't create Order"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't create Order"))
                null
            }
            updateOrder?.let {
                emit(Resource.Success(it))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateOrder(id: Int, updateOrder: UpdateOrder): Flow<Resource<Order>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val updateOrder = try {
                api.update(id, updateOrder as UpdateOrderDto)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update Order"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update Order"))
                null
            }
            updateOrder?.let {
                emit(Resource.Success(it))
            }

            emit(Resource.Loading(false))
        }
    }
}
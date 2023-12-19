package com.triforce.malacprodavac.data.repository.shops

import android.util.Log
import com.triforce.malacprodavac.data.local.MalacProdavacDatabase
import com.triforce.malacprodavac.data.mappers.toShop
import com.triforce.malacprodavac.data.remote.shops.ShopsApi
import com.triforce.malacprodavac.data.remote.shops.dto.CreateShopDto
import com.triforce.malacprodavac.domain.model.CreateShop
import com.triforce.malacprodavac.domain.model.shops.Shop
import com.triforce.malacprodavac.domain.model.shops.UpdateShop
import com.triforce.malacprodavac.domain.repository.ShopRepository
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopRepositoryImpl @Inject constructor(
    private val api: ShopsApi,
    private val db: MalacProdavacDatabase
) : ShopRepository {

    private val dao = db.shopDao

    override suspend fun registerShop(
        createShop: CreateShop
    ): Flow<Resource<Shop>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val shop = try {
                api.registerShop(
                    CreateShopDto(createShop.user, createShop.businessName)
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
            shop?.let {
                emit(Resource.Success(data = it))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun getShops(
        fetchFromRemote: Boolean,
        queryMap: MutableMap<String, String>
    ): Flow<Resource<List<Shop>>> {
        return flow {

            emit(Resource.Loading(isLoading = true))

            val localShops = dao.getShops()

            if (localShops.isNotEmpty()) {
                emit(Resource.Success(data = localShops.map { it.toShop() }))
            }

            val isDbEmpty = localShops.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteShops = try {
                api.getShops(queryMap)
            } catch (e: IOException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load shops"))
                null

            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load shops data"))
                null

            }

            remoteShops?.let {

                emit(Resource.Success(remoteShops.data.map { jt -> jt }))

            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getShop(id: Int, fetchFromRemote: Boolean): Flow<Resource<Shop>> {
        return flow {

            emit(Resource.Loading(isLoading = true))

            val localProducts = dao.getShop(id)

            if (localProducts.isNotEmpty()) {
                emit(Resource.Success(data = localProducts.first().toShop()))
            }

            val isDbEmpty = localProducts.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteShop = try {
                api.getShop(id)
            } catch (e: IOException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load shop"))
                null

            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load shop data"))
                null

            }

            remoteShop?.let {

                Log.d("SHOP:", it.toString())
                emit(Resource.Success(remoteShop))

            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateShop(shopId: Int, dto: UpdateShop): Flow<Resource<Shop>> {
        return flow {
            emit(Resource.Loading(true))

            val result = try {
                api.updateShop(shopId, dto)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update shop"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update shop data"))
                null
            }

            result?.let {
                emit(Resource.Success(it))
            }
            emit(Resource.Loading(false))
        }
    }
}
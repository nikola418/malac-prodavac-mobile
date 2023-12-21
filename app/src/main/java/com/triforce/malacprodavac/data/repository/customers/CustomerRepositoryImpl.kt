package com.triforce.malacprodavac.data.repository.customers

import com.triforce.malacprodavac.data.remote.customers.CustomersApi
import com.triforce.malacprodavac.data.remote.customers.dto.CreateCustomerDto
import com.triforce.malacprodavac.data.remote.customers.dto.CreateFavoriteProductDto
import com.triforce.malacprodavac.data.repository.customers.favoriteShops.dto.CreateFavoriteShopDto
import com.triforce.malacprodavac.domain.model.CreateCustomer
import com.triforce.malacprodavac.domain.model.Customer
import com.triforce.malacprodavac.domain.model.customers.FavoriteProduct
import com.triforce.malacprodavac.domain.model.customers.FavoriteShop
import com.triforce.malacprodavac.domain.model.customers.UpdateCustomer
import com.triforce.malacprodavac.domain.repository.CustomerRepository
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepositoryImpl @Inject constructor(
    private val api: CustomersApi
) : CustomerRepository {
    override suspend fun registerCustomer(
        createCustomer: CreateCustomer
    ): Flow<Resource<Customer>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val customer = try {
                api.registerCustomer(
                    CreateCustomerDto(createCustomer.user)
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
            customer?.let {
                emit(Resource.Success(data = it))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun getFavoriteProducts(
        customerId: Int,
        fetchFromRemote: Boolean,
        queryMap: MutableMap<String, String>
    ): Flow<Resource<List<FavoriteProduct>>> {

        return flow {

            emit(Resource.Loading(isLoading = true))

            val favoriteProducts = try {
                api.getFavoriteProducts(customerId, queryMap)

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't add favorite product."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == HttpURLConnection.HTTP_CONFLICT) {
                    emit(Resource.Error("Favorite  product doesn't exist!"))
                } else {
                    emit(Resource.Error("Couldn't add favorite product."))
                }
                null
            }
            favoriteProducts?.let {
                emit(Resource.Success(data = it.data))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun updateCustomer(
        customerId: Int,
        dto: UpdateCustomer
    ): Flow<Resource<Customer>> {
        return flow {
            emit(Resource.Loading(true))
            val result = try {
                api.updateCustomer(customerId, dto)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update customer."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message()))
                null
            }
            result?.let {
                emit(Resource.Success(it))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun insertFavoriteProduct(
        customerId: Int,
        createFavoriteProductDto: CreateFavoriteProductDto
    ): Flow<Resource<FavoriteProduct>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val favoriteProduct = try {
                api.createFavoriteProduct(customerId, createFavoriteProductDto)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't add favorite product."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == HttpURLConnection.HTTP_CONFLICT) {
                    emit(Resource.Error("Favorite  product is't exist!"))
                } else {
                    emit(Resource.Error("Couldn't add favorite product."))
                }
                null
            }
            favoriteProduct?.let {
                emit(Resource.Success(data = it))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun deleteFavoriteProduct(
        id: Int,
        favoriteProductId: Int
    ): Flow<Resource<FavoriteProduct>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val favoriteProduct = try {
                api.deleteFavoriteProduct(id, favoriteProductId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't add favorite product."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == HttpURLConnection.HTTP_CONFLICT) {
                    emit(Resource.Error("Favorite  product is't exist!"))
                } else {
                    emit(Resource.Error("Couldn't add favorite product."))
                }
                null
            }
            favoriteProduct?.let {
                emit(Resource.Success(data = it))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun getFavoriteShops(
        customerId: Int,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<FavoriteShop>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val favoriteShops = try {
                api.getFavoriteShops(
                    customerId
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't get favorite shop."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == HttpURLConnection.HTTP_CONFLICT) {
                    emit(Resource.Error("Favorite shop is't exist!"))
                } else {
                    emit(Resource.Error("Couldn't get favorite shop."))
                }
                null
            }
            favoriteShops?.let {
                emit(Resource.Success(data = it.data))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun insertFavoriteShop(
        customerId: Int,
        createFavoriteShopDto: CreateFavoriteShopDto
    ): Flow<Resource<FavoriteShop>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val favoriteShop = try {
                api.createFavoriteShop(customerId, createFavoriteShopDto)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't add favorite shop."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == HttpURLConnection.HTTP_CONFLICT) {
                    emit(Resource.Error("Favorite shop is't exist!"))
                } else {
                    emit(Resource.Error("Couldn't add favorite shop."))
                }
                null
            }
            favoriteShop?.let {
                emit(Resource.Success(data = it))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun deleteFavoriteShop(
        id: Int,
        favoriteShopId: Int
    ): Flow<Resource<FavoriteShop>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val favoriteShop = try {
                api.deleteFavoriteShop(id, favoriteShopId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't delete favorite shop."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == HttpURLConnection.HTTP_CONFLICT) {
                    emit(Resource.Error("Favorite shop is't exist!"))
                } else {
                    emit(Resource.Error("Couldn't delete favorite shop."))
                }
                null
            }
            favoriteShop?.let {
                emit(Resource.Success(data = it))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

}
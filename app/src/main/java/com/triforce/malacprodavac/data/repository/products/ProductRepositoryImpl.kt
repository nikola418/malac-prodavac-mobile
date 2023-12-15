package com.triforce.malacprodavac.data.repository.products

import android.util.Log
import com.triforce.malacprodavac.data.local.MalacProdavacDatabase
import com.triforce.malacprodavac.data.mappers.products.toProduct
import com.triforce.malacprodavac.data.remote.products.ProductsApi
import com.triforce.malacprodavac.data.services.SessionManager
import com.triforce.malacprodavac.domain.model.pagination.PaginationResult
import com.triforce.malacprodavac.domain.model.products.CreateProductDto
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.domain.model.products.UpdateProductDto
import com.triforce.malacprodavac.domain.repository.products.ProductRepository
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(

    private val api: ProductsApi,
    private val db: MalacProdavacDatabase,
    private val sessionManager: SessionManager

) : ProductRepository {

    private val dao = db.productDao

    override suspend fun getProducts(
        categoryId: Int,
        fetchFromRemote: Boolean,
        queryMap: MutableMap<String, String>
    ): Flow<Resource<PaginationResult<Product>>> {

        return flow {

            emit(Resource.Loading(isLoading = true))

//            val localProducts = dao.getProducts()
//
//            if (localProducts.isNotEmpty()) {
//                emit(Resource.Success(data = localProducts.map { it.toProduct() }))
//            }
//
//
//            val isDbEmpty = localProducts.isEmpty()
//            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
//
//            if (shouldJustLoadFromCache) {
//                emit(Resource.Loading(false))
//                return@flow
//            }

            val response = try {
                api.getProducts(queryMap)
            } catch (e: IOException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load products"))
                null

            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load products data"))
                null

            }

            response?.let {
                emit(Resource.Success(response))
            }

            emit(Resource.Loading(false))
        }

    }

    override suspend fun getProduct(id: Int, fetchFromRemote: Boolean): Flow<Resource<Product>> {
        return flow {

            emit(Resource.Loading(isLoading = true))

            val localProducts = dao.getProductForId(id)

            if (localProducts.isNotEmpty()) {
                emit(Resource.Success(data = localProducts.first().toProduct()))
            }


            val isDbEmpty = localProducts.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteProduct = try {
                api.getProduct(id)
            } catch (e: IOException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load products"))
                null

            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load products data"))
                null

            }

            remoteProduct?.let {

                Log.d("PRODUCTS:", it.toString())
                emit(Resource.Success(remoteProduct))

            }

            emit(Resource.Loading(false))
        }
    }


    override suspend fun deleteProduct(id: Int): Flow<Resource<Product>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val deletedProduct = try {
                api.delete(id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't delete Product"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't delete Product"))
                null
            }
            deletedProduct?.let {
                emit(Resource.Success(it))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun insertProduct(createProductDto: CreateProductDto): Flow<Resource<Product>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val insertedProduct = try {
                api.create(createProductDto)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't create Product"))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(e.message()))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't create Product"))
                null
            }
            insertedProduct?.let {
                emit(Resource.Success(it))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateProduct(
        id: Int,
        updateProduct: UpdateProductDto
    ): Flow<Resource<Product>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val updatedProduct = try {
                api.update(id, updateProduct)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update Product"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update Product"))
                null
            }
            updatedProduct?.let {
                emit(Resource.Success(it))
            }

            emit(Resource.Loading(false))
        }
    }

}
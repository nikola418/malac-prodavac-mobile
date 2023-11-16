package com.triforce.malacprodavac.data.repository.categories

import android.util.Log
import com.triforce.malacprodavac.data.local.MalacProdavacDatabase
import com.triforce.malacprodavac.data.mappers.toCategory
import com.triforce.malacprodavac.data.remote.categories.CategoriesApi
import com.triforce.malacprodavac.data.services.SessionManager
import com.triforce.malacprodavac.domain.model.Category
import com.triforce.malacprodavac.domain.repository.CategoryRepository
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoriesApi,
    private val db: MalacProdavacDatabase,
    private val sessionManager: SessionManager
) : CategoryRepository {
    private val dao = db.categoryDao



    override suspend fun getCategories(fetchFromRemote: Boolean): Flow<Resource<List<Category>>> {
        // ****PRIMER****   private val queryMap = FilterBuilder.buildFilterQueryMap(
        //        Filter(
        //            filter = listOf(
        //                SingleFilter("id", FilterOperation.In, listOf(1, 2, 3, 4, 5)),
        //                SingleFilter("!after", FilterOperation.Eq, 21),
        //            ),
        //            order = listOf(SingleOrder("id", FilterOrder.Asc)),
        //            offset = null, limit = 20
        //        )
        //    )
        return flow {
            emit(Resource.Loading(isLoading = true))
            val localCategories = dao.getAllCategories()
            emit(Resource.Success(
                data = localCategories.map { it.toCategory() }
            ))
            val isDbEmpty = localCategories.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) { //we already returned an emit with Resource.Success<data from cache>
                emit(Resource.Loading(false)) // stop loading indication
                return@flow
            }
            val remoteCategories = try {
                api.getAllCategories(
                    // ****PRIMER**** queryMap
                    limit = 80,
                    mutableMapOf()
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load categories."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load category data"))
                null
            }
            remoteCategories?.let {
                Log.d("CATEGORIES:", it.toString())
                emit(Resource.Success(remoteCategories.data.map { it.toCategory() }))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCategory(
        id: Int,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Category>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCategory = dao.getCategoryForId(id)
            emit(Resource.Success(
                data = localCategory.map { it.toCategory() }
            ))

            val isDbEmpty = localCategory.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) { //we already returned an emit with Resource.Success<data from cache>
                emit(Resource.Loading(false)) //stop loading indication
                return@flow
            }
            val remoteCategory = try {
                api.getCategoryForId(id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load post data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load post data"))
                null
            }
            emit(Resource.Loading(false))
        }
    }
}
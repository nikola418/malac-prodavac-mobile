package com.triforce.malacprodavac.data.repository.scheduledPickups

import android.util.Log
import com.triforce.malacprodavac.data.local.MalacProdavacDatabase
import com.triforce.malacprodavac.data.mappers.toSchedulePickups
import com.triforce.malacprodavac.data.remote.orders.OrderApi
import com.triforce.malacprodavac.data.remote.orders.dto.CreateSchedulePickupDto
import com.triforce.malacprodavac.data.remote.orders.dto.UpdateScheduledPickupDto
import com.triforce.malacprodavac.data.remote.shops.ShopsApi
import com.triforce.malacprodavac.domain.model.SchedulePickup
import com.triforce.malacprodavac.domain.model.UpdateScheduledPickup
import com.triforce.malacprodavac.domain.repository.ScheduledPickupRepository
import com.triforce.malacprodavac.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ScheduledPickupsRepositoryImpl @Inject constructor(
    private val orderApi: OrderApi,
    private val shopsApi: ShopsApi,
    private val db: MalacProdavacDatabase
) : ScheduledPickupRepository {

    private val dao = db.schedulePickupsDao

    override suspend fun getScheduledPickups(
        id: Int,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<SchedulePickup>>> {
        return flow {

            emit(Resource.Loading(isLoading = true))

            val localscheduledPickups = dao.getAllSchedules()

            if (localscheduledPickups.isNotEmpty()) {
                emit(Resource.Success(data = localscheduledPickups.map { it.toSchedulePickups() }))
            }


            val isDbEmpty = localscheduledPickups.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteSchedulePickups = try {
                shopsApi.getShopSchedulePickups(id)
            } catch (e: IOException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load schedule"))
                null

            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load schedule data"))
                null

            }

            remoteSchedulePickups?.let {

                Log.d("SCHEDULES:", it.toString())
                emit(Resource.Success(remoteSchedulePickups.data.map { jt -> jt.toSchedulePickups() }))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getScheduledPickup(
        id: Int,
        scheduledPickupId: Int,
        fetchFromRemote: Boolean
    ): Flow<Resource<SchedulePickup>> {
        return flow {

            emit(Resource.Loading(isLoading = true))

            val localSchedule = dao.getScheduleForId(id)

            if (localSchedule.isNotEmpty()) {
                emit(Resource.Success(data = localSchedule.first().toSchedulePickups()))
            }


            val isDbEmpty = localSchedule.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteSchedule = try {
                shopsApi.getShopSchedulePickupsById(id, scheduledPickupId)
            } catch (e: IOException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load schedule"))
                null

            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error("Couldn't load schedule data"))
                null

            }

            remoteSchedule?.let {

                Log.d("SCHEDULE:", it.toString())
                emit(Resource.Success(remoteSchedule.toSchedulePickups()))

            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun insertScheduledPickup(
        id: Int,
        createSchedulePickup: CreateSchedulePickupDto
    ): Flow<Resource<SchedulePickup>> {

        return flow {
            emit(Resource.Loading(isLoading = true))
            val insertSchedule = try {
                orderApi.insertScheduledPickup(id, createSchedulePickup)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                null
            }
            insertSchedule?.let {
                emit(Resource.Success(it.toSchedulePickups()))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateScheduledPickup(
        id: Int,
        scheduledPickupId: Int,
        updateSchedulePickup: UpdateScheduledPickup
    ): Flow<Resource<SchedulePickup>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val updateSchedule = try {
                orderApi.updateSchedulePickups(
                    id,
                    scheduledPickupId,
                    updateSchedulePickup as UpdateScheduledPickupDto
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update Schedule"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't update Schedule"))
                null
            }
            updateSchedule?.let {
                emit(Resource.Success(it.toSchedulePickups()))
            }

            emit(Resource.Loading(false))
        }
    }
}
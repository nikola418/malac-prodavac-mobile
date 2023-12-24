package com.triforce.malacprodavac.data.remote.customers


import com.triforce.malacprodavac.data.remote.customers.dto.CreateCustomerDto
import com.triforce.malacprodavac.data.remote.customers.dto.CreateFavoriteProductDto
import com.triforce.malacprodavac.data.remote.dto.PaginationResponse
import com.triforce.malacprodavac.data.repository.customers.favoriteShops.dto.CreateFavoriteShopDto
import com.triforce.malacprodavac.domain.model.Customer
import com.triforce.malacprodavac.domain.model.Order
import com.triforce.malacprodavac.domain.model.SchedulePickup
import com.triforce.malacprodavac.domain.model.customers.FavoriteProduct
import com.triforce.malacprodavac.domain.model.customers.FavoriteShop
import com.triforce.malacprodavac.domain.model.customers.UpdateCustomer
import com.triforce.malacprodavac.domain.model.pagination.PaginationResult
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CustomersApi {
    @POST(ROUTE)
    suspend fun registerCustomer(
        @Body registerRequest: CreateCustomerDto
    ): Customer

    @GET(ROUTE)
    suspend fun getCustomers(@QueryMap queryMap: MutableMap<String, String>): PaginationResult<Customer>

    @GET("${ROUTE}/{id}")
    suspend fun getCustomer(@Path("id") customerId: Int): Customer

    @PATCH("${ROUTE}/{id}")
    suspend fun updateCustomer(
        @Path("id") customerId: Int,
        @Body updateBody: UpdateCustomer
    ): Customer

    @POST("${ROUTE}/{id}/favoriteProducts")
    suspend fun createFavoriteProduct(
        @Path("id") customerId: Int,
        @Body() body: CreateFavoriteProductDto
    ): FavoriteProduct

    @GET("${ROUTE}/{id}/favoriteProducts")
    suspend fun getFavoriteProducts(
        @Path("id") customerId: Int,
        @QueryMap queryMap: MutableMap<String, String>
    ): PaginationResult<FavoriteProduct>

    @DELETE("${ROUTE}/{id}/favoriteProducts/{favoriteProductId}")
    suspend fun deleteFavoriteProduct(
        @Path("id") customerId: Int,
        @Path("favoriteProductId") favoriteProductId: Int
    ): FavoriteProduct

    @GET("${ROUTE}/{id}/scheduledPickups")
    suspend fun getCustomerSchedulePickups(
        @Path("id") customerId: Int,
        //@QueryMap queryMap: MutableMap<String, String>
    ): PaginationResult<SchedulePickup>

    @GET("${ROUTE}/{id}/scheduledPickups/{scheduledPickupId}")
    suspend fun getCustomerSchedulePickupsById(
        @Path("id") customerId: Int,
        @Path("scheduledPickupId") scheduledPickupId: Int
        //@QueryMap queryMap: MutableMap<String, String>
    ): SchedulePickup

    @POST("${ROUTE}/{id}/favoriteShops")
    suspend fun createFavoriteShop(
        @Path("id") customerId: Int,
        @Body() body: CreateFavoriteShopDto
    ): FavoriteShop

    @GET("${ROUTE}/{id}/favoriteShops")
    suspend fun getFavoriteShops(
        @Path("id") customerId: Int,
        //@QueryMap queryMap: MutableMap<String, String>
    ): PaginationResult<FavoriteShop>

    @DELETE("${ROUTE}/{id}/favoriteShops/{favoriteShopId}")
    suspend fun deleteFavoriteShop(
        @Path("id") customerId: Int,
        @Path("favoriteShopId") favoriteShopId: Int
    ): FavoriteShop


    @GET("${ROUTE}/{id}/orders")
    suspend fun getUserOrders(@Path("id") id: Int): PaginationResponse<Order>

    companion object {
        const val ROUTE = "/customers"
    }
}
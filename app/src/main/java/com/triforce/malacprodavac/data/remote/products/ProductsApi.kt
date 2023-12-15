package com.triforce.malacprodavac.data.remote.products

import com.triforce.malacprodavac.domain.model.pagination.PaginationResult
import com.triforce.malacprodavac.domain.model.products.CreateProductDto
import com.triforce.malacprodavac.domain.model.products.Product
import com.triforce.malacprodavac.domain.model.products.UpdateProductDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductsApi {
    @POST(ROUTE)
    suspend fun create(@Body() createPostDto: CreateProductDto): Product

    @GET(ROUTE)
    suspend fun getProducts(
        @QueryMap() queryMap: MutableMap<String, String>
    ): PaginationResult<Product>

    @GET("${ROUTE}/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    @PATCH("${ROUTE}/{id}")
    suspend fun update(
        @Path("id") id: Int,
        @Body() updateProductDto: UpdateProductDto
    ): Product

    @DELETE("${ROUTE}/{id}")
    suspend fun delete(
        @Path("id") id: Int
    ): Product

    companion object {
        const val ROUTE = "/products"
    }

}
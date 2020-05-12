package com.example.shopifymatchinggame.network

import com.example.shopifymatchinggame.model.ProductList
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface GetProductsService {

    @GET("/admin/products.json")
    fun getAllProducts(
        @Query("page") page: Number,
        @Query("access_token") accessToken: String
    ): Call<ProductList>
}
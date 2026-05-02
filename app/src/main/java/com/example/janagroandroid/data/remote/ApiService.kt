package com.example.janagroandroid.data.remote

import com.example.janagroandroid.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductDto>>
}
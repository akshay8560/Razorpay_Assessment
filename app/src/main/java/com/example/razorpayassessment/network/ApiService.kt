package com.example.razorpayassessment.network

import com.example.razorpayassessment.data.Task
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    suspend fun getTasks(): List<Task>

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
} 
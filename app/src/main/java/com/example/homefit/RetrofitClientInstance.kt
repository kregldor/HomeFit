package com.example.homefit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClientInstance {
    private val BASE_URL = "http://10.0.2.2:8080"
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }
}
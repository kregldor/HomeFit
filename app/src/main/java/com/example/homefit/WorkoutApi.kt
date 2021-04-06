package com.example.homefit

import retrofit2.Response
import retrofit2.http.GET

interface WorkoutApi {

    @GET("/list")
    suspend fun getWorkouts(): Response<List<Workout>>
}
package com.example.homefit

import retrofit2.Response

interface WorkoutRepository {
    suspend fun getWorkouts(): Response<List<Workout>>
}

@WorkoutRepositoryImpl.Singleton
class WorkoutRepositoryImpl : WorkoutRepository {
    annotation class Singleton

    private val workoutApi: WorkoutApi =
        RetrofitClientInstance().getRetrofitInstance().create(WorkoutApi::class.java)

    override suspend fun getWorkouts(): Response<List<Workout>> {
        return workoutApi.getWorkouts()
    }
}
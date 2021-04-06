package com.example.homefit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

class WorkoutTypesViewModel : ViewModel() {

    var workoutRepository: WorkoutRepository = WorkoutRepositoryImpl()
    var errorMessage = MutableLiveData<String>()

    private val allWorkout = MutableLiveData<List<Workout>?>()

    val selectedWorkout = MutableLiveData<List<Workout>>()

    init {
        getWorkouts()
    }

    private fun filterWorkouts(type: Type?) {
        selectedWorkout.postValue(allWorkout.value?.filter { it.type == type })
    }

    fun filterWorkouts(selected: String) {

        val selectedType = Type.fromString(selected)
        filterWorkouts(selectedType)
    }

    private fun getWorkouts() {
        viewModelScope.launch {
            val workoutResult = fetchWorkout()
            handleWorkoutResult(workoutResult)
        }
    }

    private suspend fun fetchWorkout(): NetworkState {
        return try {
            val response = workoutRepository.getWorkouts()
            if (response.isSuccessful) {
                NetworkState.Success(response.body()!!)
            } else {
                when (response.code()) {
                    403 -> NetworkState.HttpErrors.ResourceForbidden(response.message())
                    404 -> NetworkState.HttpErrors.ResourceNotFound(response.message())
                    500 -> NetworkState.HttpErrors.InternalServerError(response.message())
                    502 -> NetworkState.HttpErrors.BadGateWay(response.message())
                    301 -> NetworkState.HttpErrors.ResourceRemoved(response.message())
                    302 -> NetworkState.HttpErrors.RemovedResourceFound(response.message())
                    else -> NetworkState.Error("Network error, try it again")
                }
            }

        } catch (error: IOException) {
            NetworkState.NetworkException(error.message!!)
        }

    }

    private fun handleWorkoutResult(networkState: NetworkState) {
        return when (networkState) {
            is NetworkState.Success -> {
                allWorkout.postValue(networkState.data)
                selectedWorkout.postValue(networkState.data)
            }
            is NetworkState.HttpErrors.ResourceForbidden -> handleError(networkState.exception)
            is NetworkState.HttpErrors.ResourceNotFound -> handleError(networkState.exception)
            is NetworkState.HttpErrors.InternalServerError -> handleError(networkState.exception)
            is NetworkState.HttpErrors.BadGateWay -> handleError(networkState.exception)
            is NetworkState.HttpErrors.ResourceRemoved -> handleError(networkState.exception)
            is NetworkState.HttpErrors.RemovedResourceFound -> handleError(networkState.exception)
            is NetworkState.Error -> handleError(networkState.error)
            is NetworkState.NetworkException -> handleError(networkState.error)
        }

    }

    private fun handleError(message: String) {
        errorMessage.postValue(message)
    }

}
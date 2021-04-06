package com.example.homefit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {
    fun setWorkout(workout: Workout) {
        this.workout.postValue(workout)
    }


    val workout = MutableLiveData<Workout>()

    init {
    }

}
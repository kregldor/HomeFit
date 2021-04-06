package com.example.homefit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Workout(
    var name: String,
    var duration: Int,
    var level: Level,
    var type: Type,
    var description: String,
    var trainer: String? = null,
    var image: String,
    var steps: List<Step>? = null
) : Parcelable

@Parcelize
data class Step(
    var name: String,
    var sets: Int,
    var reps: Int,
    var image: String,
    var description: String? = name
) : Parcelable

enum class Type(val text: String) {
    UPPER_BODY("upper body"),
    LOWER_BODY("lower body"),
    FULL_BODY("full body");

    companion object{
        fun fromString(text: String): Type? {
            for (b in values()) {
                if (b.text == text) {
                    return b
                }
            }
            return null
        }
    }


}

enum class Level(val text: String) {
    BEGINNER("beginner"),
    ADVANCED("advanced")
}
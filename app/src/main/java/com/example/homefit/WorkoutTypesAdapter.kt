package com.example.homefit

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homefit.databinding.WorkoutViewBinding
import com.squareup.picasso.Picasso


class WorkoutTypesAdapter(
    private val onClickListener: (Workout) -> Unit
) :
    ListAdapter<Workout, WorkoutTypesAdapter.WorkoutViewHolder>(CharacterItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): WorkoutViewHolder {
        val binding = WorkoutViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class WorkoutViewHolder(private val binding: WorkoutViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(workout: Workout) {

            binding.let {
                it.workoutName.text = workout.name.toUpperCase()
                it.workoutLevel.text = workout.level.text
                it.workoutDuration.text = workout.duration.toString().plus(" min")
            }

            Picasso.get().load(workout.image).fit().centerCrop().into(binding.workoutImg)


            itemView.setOnClickListener {
                onClickListener(workout)
            }
        }


    }
}

class CharacterItemDiffCallback : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean =
        oldItem == newItem

}

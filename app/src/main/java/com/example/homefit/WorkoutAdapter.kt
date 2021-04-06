package com.example.homefit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homefit.databinding.StepViewBinding
import com.squareup.picasso.Picasso


class WorkoutAdapter(
    private val onClickListener: (Step) -> Unit
) :
    ListAdapter<Step, WorkoutAdapter.StepViewHolder>(WorkoutItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): StepViewHolder {
        val binding = StepViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class StepViewHolder(private val binding: StepViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(step: Step) {
            binding.apply {
                stepDescription.text = step.name
                sets.text = step.sets.toString().plus("x")
                reps.text = step.reps.toString()
            }

            Picasso.get().load(step.image).fit().centerCrop().into(binding.stepImg)

            itemView.setOnClickListener {
                onClickListener(step)
            }
        }

    }
}

class WorkoutItemDiffCallback : DiffUtil.ItemCallback<Step>() {
    override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean =
        oldItem == newItem

}

package com.example.homefit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homefit.databinding.FragmentWorkoutBinding
import com.squareup.picasso.Picasso

class WorkoutFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutBinding
    val viewModel: WorkoutViewModel by viewModels()
    val args: WorkoutFragmentArgs by navArgs()

    private val workoutAdapter by lazy {
        WorkoutAdapter { step ->
            val alertDialog: AlertDialog? = activity?.let { fragmentActivity ->
                val builder = AlertDialog.Builder(fragmentActivity).also{
                    it.setMessage(step.description)
                    it.setTitle(step.name)
                }


                // Create the AlertDialog
                builder.create()
            }
            alertDialog?.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.also {
            it.adapter = workoutAdapter
            it.layoutManager = LinearLayoutManager(context)
        }


        viewModel.setWorkout(args.workout)

        viewModel.workout.observe(viewLifecycleOwner, Observer { workout ->
            Picasso.get().load(workout.image).fit().centerCrop().into(binding.image)
            binding.toolbar.apply {
                title = workout.name.toUpperCase()
                subtitle = workout.duration.toString().plus(" minutes")

            }
            workoutAdapter.submitList(workout.steps)

        })

        setupToolbar()

    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
    }
}
package com.example.homefit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homefit.databinding.FragmentWorkoutTypesBinding


class WorkoutTypesFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutTypesBinding
    private val viewModel: WorkoutTypesViewModel by viewModels()

    private val workoutTypeAdapter by lazy {
        WorkoutTypesAdapter { workout ->
            val action = WorkoutTypesFragmentDirections.actionWorkoutTypesToWorkout(workout)
            view?.findNavController()?.navigate(action)
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentWorkoutTypesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typeValues = enumValues<Type>()
        typeValues.forEach {
            it.text
        }

        binding.recyclerView.also {
            it.adapter = workoutTypeAdapter
            it.layoutManager = LinearLayoutManager(context)
        }

        viewModel.selectedWorkout.observe(viewLifecycleOwner, Observer { list ->
            workoutTypeAdapter.submitList(list)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            binding.errorMessage.text = message
        })


        //TODO "none" or similar to delete choice
        val listPopupWindow = ListPopupWindow(requireContext(), null, R.attr.listPopupWindowStyle)

        // Set list popup's content
        val items = listOf(Type.LOWER_BODY.text, Type.UPPER_BODY.text, Type.FULL_BODY.text)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_window_item, items)

        // Set button as the list popup's anchor
        listPopupWindow.apply {
            anchorView = binding.listPopupButton
            setAdapter(adapter)
            setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                viewModel.filterWorkouts(items[position])
                // Dismiss popup.
                dismiss()
            }
        }
        // Show list popup window on button click.
        binding.listPopupButton.setOnClickListener {
            listPopupWindow.show()
        }

        setupToolbar()

    }


    private fun setupToolbar() {
        binding.myToolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
    }
}
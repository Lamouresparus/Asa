package com.android.asa.ui.add_course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentAddAllCoursesBinding


class AddAllCoursesFragment : Fragment() {
    private lateinit var binding: FragmentAddAllCoursesBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddAllCoursesBinding.inflate(layoutInflater)
        setUpClickListeners()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpClickListeners() {
        binding.addCoursesIv.setOnClickListener {
            findNavController().navigate(AddAllCoursesFragmentDirections.actionAddAllCoursesFragment2ToAddCourseFragment())
        }
        binding.doneButton.setOnClickListener {
            findNavController().navigate(AddAllCoursesFragmentDirections.actionAddAllCoursesFragment2ToHomeFragment())
        }
    }

}
package com.android.asa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.R
import com.android.asa.databinding.FragmentAddSemesterCoursesBinding

class AddSemesterCoursesFragment : Fragment() {
    private lateinit var binding: FragmentAddSemesterCoursesBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddSemesterCoursesBinding.inflate(layoutInflater)
        setUpClickListener()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpClickListener() {
        binding.addNewCoursesBtn.setOnClickListener {
            findNavController().navigate(AddSemesterCoursesFragmentDirections.actionAddSemesterCoursesFragmentToAddCourseFragment())
        }

        binding.profilePhotoIv.setOnClickListener {
            findNavController().navigate(R.id.action_addSemesterCoursesFragment_to_profileFragment)
        }
    }


}
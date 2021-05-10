package com.android.asa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentBeginSemesterBinding


class BeginSemesterFragment : Fragment() {

    private lateinit var binding: FragmentBeginSemesterBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBeginSemesterBinding.inflate(layoutInflater)
        setUpOnClicks()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpOnClicks() {
        binding.startNewSemesterBtn.setOnClickListener {
            findNavController().navigate(BeginSemesterFragmentDirections.actionBeginSemesterFragmentToAddSemesterCoursesFragment())

        }
    }

}
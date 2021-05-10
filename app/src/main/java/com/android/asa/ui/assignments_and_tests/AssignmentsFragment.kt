package com.android.asa.ui.assignments_and_tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.asa.databinding.FragmentAssignmentsBinding


class AssignmentsFragment : Fragment() {
    private lateinit var binding: FragmentAssignmentsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAssignmentsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }


}
package com.android.asa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.asa.databinding.FragmentHomeBinding
import com.android.asa.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
//        findNavController().navigate(BeginSemesterFragmentDirections.actionBeginSemesterFragmentToAddSemesterCoursesFragment())
        return binding.root
    }


}
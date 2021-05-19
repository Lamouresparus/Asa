package com.android.asa.ui.staff_advisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.databinding.FragmentStaffHomeBinding
import com.asa.data.sharedPreference.SharedPreferenceReader
import javax.inject.Inject

//
class StaffHomeFragment : Fragment() {


    @Inject
    lateinit var prefReader: SharedPreferenceReader

    private lateinit var binding: FragmentStaffHomeBinding


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStaffHomeBinding.inflate(layoutInflater)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        setUpRv()
        binding.staffName.text = prefReader.getUserData()?.firstName
    }

    private fun setUpRv() {
        binding.studentLevelsRv.apply {
            adapter = StudentLevelAdapter()
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

}
package com.android.asa.ui.staff_advisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.databinding.FragmentStaffHomeBinding
import com.android.asa.extensions.makeGone
import com.android.asa.extensions.makeVisible
import com.android.asa.ui.staff_advisor.models.StudentLevelInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffHomeFragment : Fragment() {

    private lateinit var binding: FragmentStaffHomeBinding

    private val viewModel: StaffHomeViewModel by viewModels()

    private val studentLevelsInfo = mutableListOf<StudentLevelInfo>()

    lateinit var studentLevelAdapter: StudentLevelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStaffHomeBinding.inflate(layoutInflater)
        setUpRv()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.getStudentLevelsInfo()
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            when (viewState) {
                StaffHomeViewModel.StaffHomeViewState.Loading -> {
                    binding.progressBar.makeVisible()
                }
                is StaffHomeViewModel.StaffHomeViewState.Content -> {
                    studentLevelsInfo.clear()
                    studentLevelsInfo.addAll(viewState.studentLevelsInfo)
                    studentLevelAdapter.notifyDataSetChanged()
                    binding.progressBar.makeGone()
                }
            }
        })

        viewModel.user.observe(viewLifecycleOwner, {
            binding.greetingsTv.text = String.format("Hi,%s", it.email)
        })
    }

    private fun setUpRv() {
        studentLevelAdapter = StudentLevelAdapter(studentLevelsInfo) {
            val direction = StaffHomeFragmentDirections.actionStaffHomeFragmentToStudentListFragment(it)
            findNavController().navigate(direction)
        }
        binding.studentLevelsRv.apply {
            adapter = studentLevelAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }
}
package com.android.asa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentBeginSemesterBinding
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.classic.chatapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BeginSemesterFragment : BaseFragment() {

    private lateinit var binding: FragmentBeginSemesterBinding

    private val viewModel by viewModels<BeginSemesterViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBeginSemesterBinding.inflate(layoutInflater)
        setUpOnClicks()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observedData()
    }

    private fun observedData() {
        viewModel.startNewSemester.observe(viewLifecycleOwner, EventObserver { result ->
            when (result) {
                is com.android.asa.utils.Result.Loading -> {
                    progressDialog.apply {
                        setMessage("starting new semester...")
                        show()
                    }
                }

                is com.android.asa.utils.Result.Success -> {
                    progressDialog.dismiss()
                    findNavController()
                        .navigate(
                            BeginSemesterFragmentDirections
                                .actionBeginSemesterFragmentToAddSemesterCoursesFragment()
                        )
                }
                is com.android.asa.utils.Result.Error -> {
                    progressDialog.dismiss()
                    showToast(result.errorMessage)
                }
            }
        })
    }

    private fun setUpOnClicks() {
        binding.startNewSemesterBtn.setOnClickListener {
            viewModel.startNewSemester()
        }
    }

}
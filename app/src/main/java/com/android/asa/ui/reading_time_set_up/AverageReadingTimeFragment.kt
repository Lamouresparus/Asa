package com.android.asa.ui.reading_time_set_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentAverageReadingTimeBinding
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.android.asa.utils.Result
import com.asa.domain.ReadingTimeSetUpUseCase
import com.classic.chatapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AverageReadingTimeFragment : BaseFragment() {

    private lateinit var binding: FragmentAverageReadingTimeBinding

    private val viewModel by activityViewModels<ReadingTimeViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAverageReadingTimeBinding.inflate(layoutInflater)
        setUpOnclick()
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun setUpOnclick() {
        binding.halfToOneHour.setOnClickListener {
            ReadingTimeViewModel.averageReadingTime = "0.5-1"
            finalizeSetUp()
        }

        binding.oneToTwoHours.setOnClickListener {
            ReadingTimeViewModel.averageReadingTime = "1-2"
            finalizeSetUp()
        }

        binding.twoToFourHours.setOnClickListener {
            ReadingTimeViewModel.averageReadingTime = "2-4"
            finalizeSetUp()
        }

    }

    private fun finalizeSetUp() {
        val params = ReadingTimeSetUpUseCase.Params(
                preferredReadingPeriod = ReadingTimeViewModel.preferredReadingPeriod,
                kindOfReader = ReadingTimeViewModel.kindOfReader,
                averageReadingTime = ReadingTimeViewModel.averageReadingTime
        )
        viewModel.uploadReadTimeSetUp(params)
    }


    private fun observeData() {

        viewModel.readingTimeSetUp.observe(viewLifecycleOwner, EventObserver { result ->

            when (result) {

                is Result.Loading -> {
                    progressDialog.apply {
                        setMessage("Finalising set up...")
                        show()
                    }
                }
                is Result.Success -> {
                    progressDialog.dismiss()
                    navigateToNextScreen()
                }

                is Result.Error -> {
                    progressDialog.dismiss()
                    showToast(result.errorMessage)
                }
            }

        })

    }


    private fun navigateToNextScreen() {
        findNavController().navigate(AverageReadingTimeFragmentDirections.actionAverageReadingTimeFragmentToAllSetFragment())

    }

}
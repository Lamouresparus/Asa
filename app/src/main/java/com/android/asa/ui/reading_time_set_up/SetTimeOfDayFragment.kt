package com.android.asa.ui.reading_time_set_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentSetTimeOfDayBinding

class SetTimeOfDayFragment : Fragment() {

    private lateinit var binding: FragmentSetTimeOfDayBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSetTimeOfDayBinding.inflate(layoutInflater)
        setUpOnClickListeners()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpOnClickListeners() {
        binding.buttonMorning.setOnClickListener {
            ReadingTimeViewModel.preferredReadTime = 1
            navigateToNextScreen()
        }

        binding.buttonNight.setOnClickListener {
            ReadingTimeViewModel.preferredReadTime = 0
            navigateToNextScreen()

        }
    }

    fun navigateToNextScreen() {
        findNavController()
                .navigate(SetTimeOfDayFragmentDirections
                        .actionSetTimeOfDayFragmentToSetReaderTypeFragment())
    }

}
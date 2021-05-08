package com.android.asa.ui.reading_time_set_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentAverageReadingTimeBinding

class AverageReadingTimeFragment : Fragment() {

    private lateinit var binding: FragmentAverageReadingTimeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAverageReadingTimeBinding.inflate(layoutInflater)
        setUpOnclick()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpOnclick() {
        binding.halfToOneHour.setOnClickListener {
            ReadingTimeActivity.averageReadingTime = "0.5-1"
            navigateToNextScreen()
        }

        binding.oneToTwoHours.setOnClickListener {
            ReadingTimeActivity.averageReadingTime = "1-2"
            navigateToNextScreen()
        }

        binding.twoToFourHours.setOnClickListener {
            ReadingTimeActivity.averageReadingTime = "2-4"
            navigateToNextScreen()
        }

    }


    private fun navigateToNextScreen() {

        findNavController().navigate(AverageReadingTimeFragmentDirections.actionAverageReadingTimeFragmentToAllSetFragment())

    }

}
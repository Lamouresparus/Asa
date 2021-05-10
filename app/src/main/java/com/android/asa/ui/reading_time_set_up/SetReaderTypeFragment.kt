package com.android.asa.ui.reading_time_set_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentSetReaderTypeBinding

class SetReaderTypeFragment : Fragment() {


    private lateinit var binding: FragmentSetReaderTypeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSetReaderTypeBinding.inflate(layoutInflater)
        setUpOnClicks()
        return binding.root
    }
    private fun setUpOnClicks() {
        binding.beforeLectures.setOnClickListener {
            ReadingTimeViewModel.kindOfReader = "before lectures"
            navigateToNextScreen()
        }

        binding.betweenLectures.setOnClickListener {
            ReadingTimeViewModel.kindOfReader = "between lectures"
            navigateToNextScreen()

        }

        binding.buttonAfterLectures.setOnClickListener {
            ReadingTimeViewModel.kindOfReader = "after lectures"
            navigateToNextScreen()

        }
    }


    private fun navigateToNextScreen() {
        findNavController().navigate(SetReaderTypeFragmentDirections.actionSetReaderTypeFragmentToAverageReadingTimeFragment())
    }

}
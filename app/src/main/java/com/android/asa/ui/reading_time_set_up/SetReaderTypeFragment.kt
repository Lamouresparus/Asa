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
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpOnClicks() {
        binding.beforeLectures.setOnClickListener {
            findNavController().navigate(SetReaderTypeFragmentDirections.actionSetReaderTypeFragmentToAverageReadingTimeFragment())
        }

        binding.betweenLectures.setOnClickListener {
            findNavController().navigate(SetReaderTypeFragmentDirections.actionSetReaderTypeFragmentToAverageReadingTimeFragment())

        }
        binding.buttonAfterLectures.setOnClickListener {
            findNavController().navigate(SetReaderTypeFragmentDirections.actionSetReaderTypeFragmentToAverageReadingTimeFragment())
        }
    }


}
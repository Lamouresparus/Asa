package com.android.asa.ui.countup_reading_timer_ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentReadingCompleteBinding
import com.android.asa.databinding.FragmentReadingProgressBinding

class ReadingCompleteFragment : Fragment() {

    private lateinit var binding: FragmentReadingCompleteBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentReadingCompleteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.done.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}
package com.android.asa.ui.reading_timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.asa.databinding.FragmentReadingTimetableBinding

class ReadingTimetableFragment : Fragment() {

    private lateinit var binding: FragmentReadingTimetableBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentReadingTimetableBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }


}
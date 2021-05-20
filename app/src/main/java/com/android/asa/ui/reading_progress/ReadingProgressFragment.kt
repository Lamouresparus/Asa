package com.android.asa.ui.reading_progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.asa.databinding.FragmentReadingProgressBinding


class ReadingProgressFragment : Fragment() {

    private lateinit var binding: FragmentReadingProgressBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentReadingProgressBinding.inflate(layoutInflater)
        return binding.root
    }


}
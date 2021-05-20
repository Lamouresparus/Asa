package com.android.asa.ui.reading_ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.asa.R
import com.android.asa.databinding.FragmentBeginSemesterBinding
import com.android.asa.databinding.FragmentReadingTimerBinding
import com.android.asa.ui.common.BaseFragment


class ReadingTimerFragment : BaseFragment() {
    private lateinit var binding: FragmentReadingTimerBinding



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReadingTimerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOnClicks()
    }

    private fun setUpOnClicks() {

    }



}
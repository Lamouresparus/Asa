package com.android.asa.ui.cgpa_projector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.asa.databinding.FragmentCgpaProjectorBinding


class CgpaProjectorFragment : Fragment() {
    private lateinit var binding: FragmentCgpaProjectorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentCgpaProjectorBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }


}
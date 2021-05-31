package com.android.asa.ui.reading_time_set_up

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.asa.MainActivity
import com.android.asa.R
import com.android.asa.databinding.FragmentAllSetBinding
import com.android.asa.extensions.makeSpannable


class AllSetFragment : Fragment() {
    private lateinit var binding: FragmentAllSetBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAllSetBinding.inflate(layoutInflater)
        setUpViews()
        setUpOnclick()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpOnclick() {
        binding.button.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setUpViews() {
        val text: String = binding.textView12.text.toString()
        val spannableString = text.makeSpannable(16, 20, color = ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        binding.textView12.text = spannableString
    }

}
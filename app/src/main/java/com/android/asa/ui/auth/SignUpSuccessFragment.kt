package com.android.asa.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.asa.databinding.FragmentSignUpSuccessBinding
import com.android.asa.ui.reading_time_set_up.ReadingTimeActivity

class SignUpSuccessFragment : Fragment() {

    private lateinit var binding: FragmentSignUpSuccessBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSignUpSuccessBinding.inflate(layoutInflater)
        setUpOnClick()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpOnClick() {
        binding.buttonContinue.setOnClickListener {
            startActivity(Intent(requireContext(), ReadingTimeActivity::class.java))
            requireActivity().finish()
        }
    }


}
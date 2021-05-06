package com.android.asa.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentSelectUserTypeBinding

class SelectUserTypeFragment : Fragment() {
    private lateinit var binding: FragmentSelectUserTypeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSelectUserTypeBinding.inflate(layoutInflater)
        setupOnClick()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupOnClick() {
        binding.signInAsStaffAdviserTv.setOnClickListener {
            findNavController().navigate(SelectUserTypeFragmentDirections.actionSelectUserTypeFragmentToStaffAdviserSignInFragment())

        }
        binding.signInAsStudentTv.setOnClickListener {
            findNavController().navigate(SelectUserTypeFragmentDirections.actionSelectUserTypeFragmentToStudentSignInFragment())
        }
    }


}
package com.android.asa.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentStudentSignUpBinding

class StudentSignUpFragment : Fragment() {

    private lateinit var binding: FragmentStudentSignUpBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentStudentSignUpBinding.inflate(layoutInflater)
        setUpClickListeners()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpClickListeners() {
        binding.studentSignUpButton.setOnClickListener {
           findNavController().navigate(StudentSignUpFragmentDirections.actionStudentSignUpFragmentToSignUpSuccessFragment())
        }
        binding.moveToSignInScreen.setOnClickListener {
            findNavController().navigate(StudentSignUpFragmentDirections.actionStudentSignUpFragmentToStudentSignInFragment())
        }
    }


}
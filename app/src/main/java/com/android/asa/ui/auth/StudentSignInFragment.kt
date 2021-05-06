package com.android.asa.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.MainActivity
import com.android.asa.databinding.FragmentStudentSignInBinding


class StudentSignInFragment : Fragment() {

    private lateinit var binding: FragmentStudentSignInBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentStudentSignInBinding.inflate(layoutInflater)
        setUpOnClicks()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpOnClicks() {
        binding.buttonSignIn.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
        binding.moveToStudentSignUpScreen.setOnClickListener {
            findNavController().navigate(StudentSignInFragmentDirections.actionStudentSignInFragmentToStudentSignUpFragment())
        }
    }

}
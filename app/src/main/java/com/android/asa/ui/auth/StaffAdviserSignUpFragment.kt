package com.android.asa.ui.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentStaffAdviserSignUpBinding


class StaffAdviserSignUpFragment : Fragment() {

    private lateinit var binding: FragmentStaffAdviserSignUpBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,

                              savedInstanceState: Bundle?): View {
        binding = FragmentStaffAdviserSignUpBinding.inflate(layoutInflater)
        setUpOnClicks()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpOnClicks() {
        binding.moveToSignInScreen.setOnClickListener {
            findNavController().navigate(StaffAdviserSignUpFragmentDirections.actionStaffAdviserSignUpFragmentToStaffAdviserSignInFragment())
        }

        binding.staffButtonSignUp.setOnClickListener {
            //move to staff adviser home
        }
    }
}
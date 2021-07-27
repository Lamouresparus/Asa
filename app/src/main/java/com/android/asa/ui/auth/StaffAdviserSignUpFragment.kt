package com.android.asa.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentStaffAdviserSignUpBinding
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.android.asa.ui.staff_advisor.StaffHomeActivity
import com.android.asa.utils.Result
import com.asa.domain.RegisterUseCase
import com.classic.chatapp.utils.EventObserver

class StaffAdviserSignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentStaffAdviserSignUpBinding

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffAdviserSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOnClicks()
        observeData()
    }

    private fun setUpOnClicks() {
        binding.moveToSignInScreen.setOnClickListener {
            findNavController().navigate(StaffAdviserSignUpFragmentDirections.actionStaffAdviserSignUpFragmentToStaffAdviserSignInFragment())
        }

        binding.staffButtonSignUp.setOnClickListener {
            viewModel.registerUser(getSignUpParamsFromUserInputs())
        }
    }

    private fun observeData() {
        viewModel.registerResponse.observe(viewLifecycleOwner, EventObserver { result ->
            when (result) {
                is Result.Loading -> {
                    progressDialog.apply {
                        setMessage("Creating staff account...")
                        show()
                    }
                }

                is Result.Success -> {
                    progressDialog.dismiss()
                    requireActivity().apply {
                        startActivity(StaffHomeActivity.intent(requireContext()))
                        finish()
                    }
                }

                is Result.Error -> {
                    progressDialog.dismiss()
                    showToast(result.errorMessage)
                }
            }
        })
    }

    private fun getSignUpParamsFromUserInputs(): RegisterUseCase.Params {
        val password = binding.passwordEt.text.toString().trim()
        val email = binding.emailEt.text.toString().trim()
        val staffNumber = binding.staffNoEt.text.toString().trim()
        return RegisterUseCase.StaffParams(email, password, staffNumber)
    }
}
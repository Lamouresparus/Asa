package com.android.asa.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentStaffAdviserSignInBinding
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.android.asa.ui.staff_advisor.StaffHomeActivity
import com.android.asa.utils.Result
import com.asa.domain.LogInUseCase
import com.classic.chatapp.utils.EventObserver


class StaffAdviserSignInFragment : BaseFragment() {

    private lateinit var binding: FragmentStaffAdviserSignInBinding

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentStaffAdviserSignInBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setUpOnClicks()
    }

    private fun setUpOnClicks() {
        binding.moveToSignUpScreen.setOnClickListener {
            findNavController().navigate(StaffAdviserSignInFragmentDirections.actionStaffAdviserSignInFragmentToStaffAdviserSignUpFragment())
        }
        binding.buttonSignIn.setOnClickListener {
            viewModel.loginUser(getSignInParamsFromUserInputs())
        }

    }

    private fun observeData() {
        viewModel.loginResponse.observe(viewLifecycleOwner, EventObserver { result ->
            when (result) {
                is Result.Loading -> {
                    progressDialog.apply {
                        setMessage("logging in...")
                        show()
                    }
                }

                is Result.Success -> {
                    progressDialog.dismiss()
                    showToast("login successful...")
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

    private fun getSignInParamsFromUserInputs(): LogInUseCase.Params {
        val password = binding.passwordEt.text.toString().trim()
        val email = binding.emailEt.text.toString().trim()
        return LogInUseCase.Params(email, password, UserType.STAFF.ordinal)
    }

}
package com.android.asa.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.asa.MainActivity
import com.android.asa.databinding.FragmentStudentSignInBinding
import com.android.asa.extensions.closeKeyboard
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.asa.domain.LogInUseCase
import com.classic.chatapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StudentSignInFragment : BaseFragment() {

    private lateinit var binding: FragmentStudentSignInBinding

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentStudentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setUpOnClicks()
    }

    private fun setUpOnClicks() {
        binding.buttonSignIn.setOnClickListener {
            viewModel.loginUser(getSignInParamsFromUserInputs())
            closeKeyboard()
        }
        binding.moveToStudentSignUpScreen.setOnClickListener {
            findNavController().navigate(StudentSignInFragmentDirections.actionStudentSignInFragmentToStudentSignUpFragment())
        }
    }

    private fun observeData() {
        viewModel.loginResponse.observe(viewLifecycleOwner, EventObserver { result ->
            when (result) {
                is com.android.asa.utils.Result.Loading -> {
                    progressDialog.apply {
                        setMessage("logging in...")
                    }
                }

                is com.android.asa.utils.Result.Success -> {
                    progressDialog.dismiss()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                }
                is com.android.asa.utils.Result.Error -> {
                    progressDialog.dismiss()
                    showToast(result.errorMessage)
                }
            }
        })
    }

    private fun getSignInParamsFromUserInputs(): LogInUseCase.Params {
        val password = binding.passwordEt.text.toString().trim()
        val email = binding.emailEt.text.toString().trim()
        return LogInUseCase.Params(email, password, UserType.STUDENT.ordinal)
    }

}
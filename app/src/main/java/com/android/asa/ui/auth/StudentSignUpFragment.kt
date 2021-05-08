package com.android.asa.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.asa.databinding.FragmentStudentSignUpBinding
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.android.asa.utils.Result
import com.asa.domain.RegisterUseCase
import com.classic.chatapp.utils.EventObserver

class StudentSignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentStudentSignUpBinding

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentStudentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.studentSignUpButton.setOnClickListener {
            viewModel.registerUser(getSignUpParamsFromUserInputs())
        }
        binding.moveToSignInScreen.setOnClickListener {
            findNavController().navigate(StudentSignUpFragmentDirections.actionStudentSignUpFragmentToStudentSignInFragment())
        }
    }

    private fun observeData() {
        viewModel.registerResponse.observe(viewLifecycleOwner, EventObserver { result ->
            when (result) {
                is Result.Loading -> {
                    progressDialog.apply {
                        setMessage("Registering user...")
                        show()
                    }
                }

                is Result.Success -> {
                    progressDialog.dismiss()
                    findNavController().navigate(StudentSignUpFragmentDirections.actionStudentSignUpFragmentToSignUpSuccessFragment())
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
        val regNo = binding.regNoEt.text.toString().trim()
        return RegisterUseCase.StudentParams(email, password, regNo)
    }
}
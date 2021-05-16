package com.android.asa.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.asa.R
import com.android.asa.databinding.FragmentStudentSignUpBinding
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.android.asa.utils.Result
import com.asa.domain.RegisterUseCase
import com.classic.chatapp.utils.EventObserver

@Suppress("DEPRECATION")
class StudentSignUpFragment : BaseFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentStudentSignUpBinding

    private val viewModel by activityViewModels<AuthViewModel>()

    private val levelList: ArrayList<String> = arrayListOf()

    private var level = "Select Level"

            override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStudentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setUpViews()
    }

    private fun setUpViews() {
        setUpClickListeners()
        setUpSpinner()
    }

    private fun setUpSpinner() {
        levelList.apply {
            add("Select Level")
            add("100")
            add("200")
            add("300")
            add("400")
            add("500")
            add("600")
            add("700")
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, levelList)

        adapter.setDropDownViewResource(R.layout.spinner_layout)
        binding.levelSpinner.onItemSelectedListener= this

        binding.levelSpinner.adapter = adapter



    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        level = levelList[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


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
                        setMessage("Creating student account...")
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
        val firstName = binding.firstNameEt.text.toString().trim()
        val lastName = binding.lastNameEt.text.toString().trim()
        return RegisterUseCase.StudentParams(email, password, regNo, firstName, lastName, level = level.toInt())
    }
}
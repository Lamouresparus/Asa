package com.android.asa.ui.add_course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.android.asa.R
import com.android.asa.databinding.FragmentAddAllCoursesBinding
import com.android.asa.extensions.showToast
import com.android.asa.utils.Result

class ViewAllCoursesAddedFragment : Fragment() {

    private lateinit var binding: FragmentAddAllCoursesBinding

    private val viewModel by activityViewModels<AddCoursesViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddAllCoursesBinding.inflate(layoutInflater)
        setUpClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCourses()
        observeData()
    }

    private fun observeData() {
        viewModel.courses.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                }

                is Result.Success -> {
                    val listOfCourses = result.data
                    // TODO populate adapter here
                }
                is Result.Error -> {
                    showToast(result.errorMessage)
                }
            }

        })

    }

    private fun setUpClickListeners() {
        binding.addCoursesIv.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.doneButton.setOnClickListener {
            findNavController()
                    .navigate(ViewAllCoursesAddedFragmentDirections.actionAddAllCoursesFragment2ToHomeFragment(),
                            navOptions { popUpTo = R.id.homeFragment })
        }
    }

}
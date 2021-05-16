package com.android.asa.ui.add_course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.android.asa.databinding.FragmentAddAllCoursesBinding
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.android.asa.utils.Result

class ViewAllCoursesAddedFragment : BaseFragment() {

    private lateinit var binding: FragmentAddAllCoursesBinding

    private var allCoursesAdapter = AllCoursesAdapter()

    private val viewModel by activityViewModels<AddCoursesViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddAllCoursesBinding.inflate(layoutInflater)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        setUpRecyclerViews()
        setUpClickListeners()
    }

    private fun setUpRecyclerViews() {
        binding.courseOfferedRv
                .apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter = allCoursesAdapter
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCourses()
        observeData()
    }

    private fun observeData() {
        viewModel.courses.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> showProgressDialog("Fetching courses...")

                is Result.Success -> {
                    val listOfCourses = result.data
                    if (listOfCourses != null) allCoursesAdapter.setCourses(listOfCourses)
                    else showToast("you have no courses added yet")
                    hideProgressDialog()

                }
                is Result.Error -> {
                    showToast(result.errorMessage)
                    hideProgressDialog()
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
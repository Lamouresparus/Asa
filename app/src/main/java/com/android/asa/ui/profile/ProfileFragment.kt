package com.android.asa.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.asa.R
import com.android.asa.databinding.FragmentProfileBinding
import com.android.asa.extensions.loadImage
import com.android.asa.extensions.makeGone
import com.android.asa.extensions.makeVisible
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.android.asa.ui.countup_reading_timer_ui.UserCourses
import com.android.asa.ui.reading_timetable.ReadTimeTableCoursesAdapter
import com.asa.domain.model.CourseTotalReadingHoursDomain
import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserDomain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel by activityViewModels<ProfileViewModel>()

    private var readingCourses = mutableListOf<CourseTotalReadingHoursDomain>()

    private lateinit var readTimeTableCoursesAdapter: ReadTimeTableCoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpClickListeners()
        observeData()
    }

    fun observeData() {
        viewModel.getViewContent()
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            when (viewState) {
                is ProfileViewModel.ViewState.Content -> {
                    readingCourses.clear()
                    readingCourses.addAll(viewState.courses)
                    readTimeTableCoursesAdapter.notifyDataSetChanged()
                    binding.readingCoursesProgressBar.makeGone()
                    bindSemesterInfoToUI(viewState.semesterInfo)
                }
                is ProfileViewModel.ViewState.Error -> {
                    showToast(viewState.message)
                    binding.readingCoursesProgressBar.makeGone()
                }
                ProfileViewModel.ViewState.Loading -> {
                    binding.readingCoursesProgressBar.makeVisible()
                }
            }
        })

        viewModel.user.observe(viewLifecycleOwner, Observer {
            bindUserDataToUI(it)
        })
    }

    private fun bindUserDataToUI(user: UserDomain) {
        binding.shapeableImageView.loadImage(user.imageUrl, placeholder = R.drawable.user_profile_img)
        binding.userBio.text = user.userBio
        if (user.firstName != null && user.lastName != null) {
            binding.userName.text = String.format("%s, %s", user.lastName, user.firstName)
        }

        if (user.department != null && user.institution != null) {
            binding.userInstitution.text = String.format("%s, %s", user.department, user.institution)
        }
    }

    private fun bindSemesterInfoToUI(semesterDomain: SemesterDomain) {
        binding.userCreditCount.text = semesterDomain.totalCreditUnit.toString()
        binding.userCourseCount.text = semesterDomain.noOfCoursesOffered.toString()
    }

    private fun setUpClickListeners() {
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpRecycler() {
        readTimeTableCoursesAdapter = ReadTimeTableCoursesAdapter(readingCourses)
        binding.coursesRecyclerView.apply {
            adapter = readTimeTableCoursesAdapter
//            addItemDecoration(CoursesItemDecorator(3,40,true))
            layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
        }
    }

    private val onCourseItemClickCallBack: (UserCourses) -> Unit = { course ->

        val bundle = Bundle().apply {
            putParcelable("userCourses", course)
        }
        findNavController().navigate(
            R.id.action_profileFragment_to_readingTimerFragment,
            bundle
        )
    }
}
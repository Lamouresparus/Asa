package com.android.asa.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.asa.R
import com.android.asa.databinding.FragmentProfileBinding
import com.android.asa.databinding.LayoutItemForUserCoursesBinding
import com.android.asa.ui.common.BaseFragment
import com.android.asa.ui.widget.RecyclerViewAdapter
import com.android.asa.ui.widget.ViewHolder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
        setUpClickListeners()

    }

    private fun setUpClickListeners(){
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

    }
    private fun setUpRecycler() {
        binding.coursesRecyclerView.apply {
            adapter = coursesAdapter
//            addItemDecoration(CoursesItemDecorator(3,40,true))
            layoutManager = GridLayoutManager(requireContext(),3,LinearLayoutManager.VERTICAL,false)
        }

        coursesAdapter.submitList(viewModel.coursesList)

    }



    private val coursesAdapter =
            object : RecyclerViewAdapter<UserCourses>(
                    CoursesDiffUtil()
            ) {
                override fun getLayoutRes(model: UserCourses): Int {
                    return R.layout.layout_item_for_user_courses
                }

                override fun getViewHolder(
                        view: View,
                        recyclerViewAdapter: RecyclerViewAdapter<UserCourses>
                ): ViewHolder<UserCourses> {
                    return ProfileViewHolder(LayoutItemForUserCoursesBinding.bind(view),onCourseItemClickCallBack)
                }
            }

    private val onCourseItemClickCallBack: (UserCourses) -> Unit = {course->

        val bundle = Bundle().apply {
            putParcelable("userCourses", course)
        }
        findNavController().navigate(
                R.id.action_profileFragment_to_readingTimerFragment,
                bundle
        )
    }
}
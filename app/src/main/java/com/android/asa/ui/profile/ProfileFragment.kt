package com.android.asa.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.asa.R
import com.android.asa.databinding.FragmentProfileBinding
import com.android.asa.databinding.LayoutItemForUserCoursesBinding
import com.android.asa.ui.widget.RecyclerViewAdapter
import com.android.asa.ui.widget.ViewHolder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()

    }
    private fun setUpRecycler() {
        binding.coursesRecyclerView.apply {
            adapter = coursessAdapter
            addItemDecoration(CoursesItemDecorator(3,40,true))
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

    private val coursessAdapter =
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
                    return ProfileViewHolder(LayoutItemForUserCoursesBinding.bind(view))
                }

            }
}
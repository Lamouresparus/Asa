package com.android.asa.ui.profile

import com.android.asa.databinding.LayoutItemForUserCoursesBinding
import com.android.asa.ui.widget.ViewHolder

class ProfileViewHolder (private val binding: LayoutItemForUserCoursesBinding) :
        ViewHolder<UserCourses>(binding){


    override fun bind(element: UserCourses) {
        binding.courseCode.text = element.courseCode
        binding.courseProgress.text = element.CourseProgress
    }

}


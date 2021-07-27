package com.android.asa.ui.profile

import android.graphics.Color
import com.android.asa.databinding.LayoutItemForUserCoursesBinding
import com.android.asa.ui.widget.ViewHolder
import com.android.asa.ui.countup_reading_timer_ui.UserCourses
import com.android.asa.utils.ColorUtils

class ProfileViewHolder (private val binding: LayoutItemForUserCoursesBinding,
                         private val onCourseItemClick: (UserCourses) -> Unit,) :
        ViewHolder<UserCourses>(binding){

    override fun bind(element: UserCourses) {
        val color = ColorUtils.getCourseCardColor(absoluteAdapterPosition)
        binding.root.setCardBackgroundColor(Color.parseColor(color))
        binding.courseCode.text = element.courseCode
        binding.courseProgress.text = element.CourseProgress

        binding.root.setOnClickListener {
            onCourseItemClick(element)
        }
    }

}


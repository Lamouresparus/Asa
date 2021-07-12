package com.android.asa.ui.profile

import android.graphics.Color
import com.android.asa.databinding.LayoutItemForUserCoursesBinding
import com.android.asa.ui.widget.ViewHolder
import com.android.asa.ui.countup_reading_timer_ui.UserCourses

class ProfileViewHolder (private val binding: LayoutItemForUserCoursesBinding,
                         private val onCourseItemClick: (UserCourses) -> Unit,) :
        ViewHolder<UserCourses>(binding){

    val cardBackgroundColors = listOf<String>("#00BBBA","#72ED77","#FFAD00","#EB5757","#BB6BD9","#4F4F4F")

    override fun bind(element: UserCourses) {
        val randomColorIndex = (0..5).random()
        binding.root.setCardBackgroundColor(Color.parseColor(cardBackgroundColors[randomColorIndex]))
        binding.courseCode.text = element.courseCode
        binding.courseProgress.text = element.CourseProgress

        binding.root.setOnClickListener {
            onCourseItemClick(element)
        }
    }

}


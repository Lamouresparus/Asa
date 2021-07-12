package com.android.asa.ui.profile

import androidx.recyclerview.widget.DiffUtil
import com.android.asa.ui.countup_reading_timer_ui.UserCourses

class CoursesDiffUtil : DiffUtil.ItemCallback<UserCourses>() {
    override fun areItemsTheSame(oldItem: UserCourses, newItem: UserCourses): Boolean {
        return oldItem.courseCode == newItem.courseCode
    }



    override fun areContentsTheSame(
            oldItem: UserCourses,
            newItem: UserCourses
    ): Boolean {
        return oldItem == newItem
    }
}
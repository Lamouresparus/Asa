package com.android.asa.ui.profile

import androidx.recyclerview.widget.DiffUtil

data class UserCourses(val courseCode:String,val CourseProgress:String)

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
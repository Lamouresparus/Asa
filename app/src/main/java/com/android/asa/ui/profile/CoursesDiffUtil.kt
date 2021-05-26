package com.android.asa.ui.profile

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserCourses(val courseCode:String,val CourseProgress:String): Parcelable

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
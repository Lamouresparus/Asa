package com.android.asa.ui.profile

import com.android.asa.ui.common.BaseViewModel

class ProfileViewModel: BaseViewModel()  {

    val coursesList = listOf<UserCourses>(
            UserCourses("MEE 201","23"),
            UserCourses("MATH 301","23"),
            UserCourses("EEE 202","23"),
            UserCourses("CPE 101","23"),
            UserCourses("ECO 212","23"),
            UserCourses("MATH 101","23"),
            UserCourses("AEE 232","23"),
            UserCourses("MEE 207","23"),
            UserCourses("MEE 209","23")
    )
}
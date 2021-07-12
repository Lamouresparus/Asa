package com.android.asa.ui.profile

import com.android.asa.ui.common.BaseViewModel
import com.android.asa.ui.countup_reading_timer_ui.UserCourses
import com.asa.data.sharedPreference.SharedPreferenceReader
import com.asa.data.sharedPreference.SharedPreferenceWriter
import com.asa.data.sources.DataSourceFactory
import com.asa.domain.model.UserCoursesDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val dataSource: DataSourceFactory,
        private val prefWriter: SharedPreferenceWriter,
        private val preferenceReader: SharedPreferenceReader
) : BaseViewModel() {

    fun saveUserCourse(userCourses: UserCourses) {
       prefWriter.saveUserCourse(userCourses.toUserCourseDomain())
    }

    var showTimerCountDown = false

    fun getUserCourse():UserCourses = preferenceReader.getCourseDetail()!!.toUserCourse()

    val coursesList = listOf<UserCourses>(
            UserCourses("MEE 201", "23"),
            UserCourses("MATH 301", "23"),
            UserCourses("EEE 202", "22"),
            UserCourses("CPE 101", "23"),
            UserCourses("ECO 212", "56"),
            UserCourses("MATH 101", "83"),
            UserCourses("AEE 232", "23"),
            UserCourses("MEE 207", "79"),
            UserCourses("MEE 209", "100")
    )

    fun UserCourses.toUserCourseDomain() = UserCoursesDomain(
            courseCode = courseCode,
            CourseProgress = CourseProgress
    )

    fun UserCoursesDomain.toUserCourse() = UserCourses( courseCode = courseCode,
            CourseProgress = CourseProgress)
}
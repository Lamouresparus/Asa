package com.asa.local.sharedPreference

import android.content.SharedPreferences
import com.asa.data.sharedPreference.SharedPreferenceKeys
import com.asa.data.sharedPreference.SharedPreferenceReader
import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserCoursesDomain
import com.asa.domain.model.UserDomain
import javax.inject.Inject

class SharedPreferenceReaderImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val keys: SharedPreferenceKeys
) : SharedPreferenceReader {

    override fun isAppFirstLaunch(): Boolean {
        return getBoolean(keys.KEY_IS_APP_FIRST_LAUNCH, true)
    }

    override fun isLoggedIn(): Boolean {
        return getUserData() != null
    }

    override fun getSemesterInformation(): SemesterDomain {
        val hasSemesterBegun = getBoolean(keys.KEY_HAS_BEGUN_SEMESTER)
        val numOfCoursesRegistered = getInt(keys.KEY_NO_OF_COURSES_OFFERED)
        return SemesterDomain(hasSemesterBegun, numOfCoursesRegistered)
    }

    override fun getUserData(): UserDomain? {
        val userType: Int = getInt(keys.KEY_USER_TYPE, -1)
        val email: String? = getString(keys.KEY_USER_EMAIL)
        val userId: String? = getString(keys.KEY_USER_ID)
        val userFirstName: String? = getString(keys.KEY_USER_FIRST_NAME)
        val userLastName: String? = getString(keys.KEY_USER_LAST_NAME)
        val isRegistrationComplete = getBoolean(keys.KEY_USER_REGISTRATION_STATUS)
        val registrationNumber = getString(keys.KEY_USER_REGISTRATION_NUMBER)
        val staffId = getString(keys.KEY_USER_STAFF_ID)



        return if (userId.isNullOrEmpty() || email.isNullOrEmpty()) null
        else UserDomain(
            userId,
            email,
            userType,
            isRegistrationComplete,
            registrationNumber,
            staffId,
            userFirstName,
            userLastName
        )
    }

    override fun isNotificationEnabled(): Boolean {
        return sharedPreferences.getBoolean(keys.KEY_ENABLE_NOTIFICATION, true)
    }

    override fun getCourseDetail(): UserCoursesDomain? {
        val courseCode: String? = getString(keys.KEY_USER_COURSE_CODE)
        val CourseProgress: String? = getString(keys.KEY_USER_COURSE_PROGRESS)

        return if (courseCode.isNullOrEmpty() || CourseProgress.isNullOrEmpty()) null
        else UserCoursesDomain(
                courseCode,
                CourseProgress
        )
    }

    override fun getUserId(): String? {
        return sharedPreferences.getString(keys.KEY_USER_ID, null)
    }

    private fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    private fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    private fun getString(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }
}
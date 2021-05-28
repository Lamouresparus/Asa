package com.asa.local.sharedPreference

import android.content.SharedPreferences
import com.asa.data.sharedPreference.SharedPreferenceKeys
import com.asa.data.sharedPreference.SharedPreferenceWriter
import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserCoursesDomain
import com.asa.domain.model.UserDomain
import javax.inject.Inject

class SharedPreferenceWriterImpl @Inject constructor(
    sharedPreferences: SharedPreferences,
    private val keys: SharedPreferenceKeys
) : SharedPreferenceWriter {

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        saveBoolean(keys.KEY_IS_APP_FIRST_LAUNCH, isFirstLaunch)
    }

    override fun saveUser(user: UserDomain) {
        saveString(keys.KEY_USER_ID, user.userId)
        saveInt(keys.KEY_USER_TYPE, user.userType)
        saveString(keys.KEY_USER_EMAIL, user.email)
        saveString(keys.KEY_USER_FIRST_NAME, user.firstName)
        saveString(keys.KEY_USER_LAST_NAME, user.lastName)
        saveBoolean(keys.KEY_USER_REGISTRATION_STATUS, user.isRegistrationComplete)
    }

    override fun saveSemesterInformation(semester: SemesterDomain) {
        saveBoolean(keys.KEY_HAS_BEGUN_SEMESTER, semester.hasSemesterBegun)
        saveInt(keys.KEY_NO_OF_COURSES_OFFERED, semester.noOfCoursesOffered)
    }

    override fun saveUserCourse(userCoursesDomain: UserCoursesDomain) {
        saveString(keys.KEY_USER_COURSE_CODE, userCoursesDomain.courseCode)
        saveString(keys.KEY_USER_COURSE_PROGRESS, userCoursesDomain.CourseProgress)
    }

    override fun enableNotification(enable: Boolean) {
        saveBoolean(keys.KEY_ENABLE_NOTIFICATION, enable)
    }

    override fun saveUserLocation(location: String) {
        saveString(keys.KEY_USER_LOCATION, location)
    }

    override fun saveUserDescription(description: String) {
        saveString(keys.KEY_USER_DESCRIPTION, description)
    }


    override fun clearKeys(keys: List<String>) {
        for (key: String in keys) {
            editor.remove(key)
        }
        editor.apply()
    }

    private fun saveLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    private fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun saveString(key: String, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    private fun saveInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    private fun saveStringSet(key: String, value: List<String>) {
        editor.putStringSet(key, value.toSet())
        editor.apply()
    }
}
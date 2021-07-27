package com.asa.local.sharedPreference

import android.content.SharedPreferences
import com.asa.data.sharedPreference.SharedPreferenceKeys
import com.asa.data.sharedPreference.SharedPreferenceReader
import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserCoursesDomain
import com.asa.domain.model.UserDomain
import com.google.gson.Gson
import javax.inject.Inject

class SharedPreferenceReaderImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val keys: SharedPreferenceKeys,
    private val gson: Gson
) : SharedPreferenceReader {

    override fun isAppFirstLaunch(): Boolean {
        return getBoolean(keys.KEY_IS_APP_FIRST_LAUNCH, true)
    }

    override fun isLoggedIn(): Boolean {
        return getUserData() != null
    }

    override fun getSemesterInformation(): SemesterDomain? {
        val jsonString = sharedPreferences.getString(keys.KEY_SEMESTER_INFORMATION, null)
        return gson.fromJson(jsonString, SemesterDomain::class.java)
    }

    override fun getUserData(): UserDomain? {
        val jsonString = sharedPreferences.getString(keys.KEY_USER, null)
        return gson.fromJson(jsonString, UserDomain::class.java)
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
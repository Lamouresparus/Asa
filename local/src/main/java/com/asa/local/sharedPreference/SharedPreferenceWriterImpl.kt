package com.asa.local.sharedPreference

import android.content.SharedPreferences
import com.asa.data.sharedPreference.SharedPreferenceKeys
import com.asa.data.sharedPreference.SharedPreferenceWriter
import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserCoursesDomain
import com.asa.domain.model.UserDomain
import com.google.gson.Gson
import org.json.JSONStringer
import javax.inject.Inject

class SharedPreferenceWriterImpl @Inject constructor(
    sharedPreferences: SharedPreferences,
    private val keys: SharedPreferenceKeys,
    private val gson: Gson
) : SharedPreferenceWriter {

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        saveBoolean(keys.KEY_IS_APP_FIRST_LAUNCH, isFirstLaunch)
    }

    override fun saveUser(user: UserDomain) {
        val jsonString = gson.toJson(user, UserDomain::class.java)
        saveString(keys.KEY_USER, jsonString)
    }

    override fun saveSemesterInformation(semester: SemesterDomain) {
        val jsonString = gson.toJson(semester, SemesterDomain::class.java)
        saveString(keys.KEY_SEMESTER_INFORMATION, jsonString)
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
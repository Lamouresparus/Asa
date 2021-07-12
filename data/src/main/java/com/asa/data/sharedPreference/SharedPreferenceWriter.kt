package com.asa.data.sharedPreference

import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserCoursesDomain
import com.asa.domain.model.UserDomain


interface SharedPreferenceWriter {
    fun setIsFirstLaunch(isFirstLaunch: Boolean)
    fun saveUser(user: UserDomain)
    fun enableNotification(enable: Boolean)
    fun saveUserLocation(location: String)
    fun saveUserDescription(description: String)
    fun clearKeys(keys: List<String>)
    fun saveSemesterInformation(semester: SemesterDomain)
    fun saveUserCourse(userCoursesDomain: UserCoursesDomain)
}
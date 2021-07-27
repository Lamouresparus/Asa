package com.asa.data.sharedPreference

import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserDomain
import com.asa.domain.model.UserCoursesDomain


interface SharedPreferenceReader {

    fun isAppFirstLaunch(): Boolean

    fun getUserId(): String?

    fun isLoggedIn(): Boolean

    fun getUserData(): UserDomain?

    fun getSemesterInformation(): SemesterDomain?

    fun isNotificationEnabled(): Boolean

    fun getCourseDetail():UserCoursesDomain?

}
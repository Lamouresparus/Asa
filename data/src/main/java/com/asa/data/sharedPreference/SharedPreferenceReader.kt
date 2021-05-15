package com.asa.data.sharedPreference

import com.asa.domain.model.UserDomain


interface SharedPreferenceReader {

    fun isAppFirstLaunch(): Boolean

    fun getUserId(): String?

    fun isLoggedIn(): Boolean

    fun getUserData(): UserDomain?

    fun isNotificationEnabled(): Boolean

}
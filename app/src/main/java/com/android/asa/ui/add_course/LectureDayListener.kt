package com.android.asa.ui.add_course

import com.asa.domain.model.LectureDayDomain

interface LectureDayListener {
    fun isChecked(day: LectureDayDomain)
    fun isUnchecked(dayOfWeek: String)
}
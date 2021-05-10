package com.android.asa.ui.add_course

import android.widget.TextView

interface LectureDayListener {
    fun isChecked(day: String)
    fun isUnchecked(day: String)
    fun setLectureTime(tv: TextView, text: String)
}
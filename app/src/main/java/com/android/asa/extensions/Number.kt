package com.android.asa.extensions

import android.content.res.Resources

class Number {
    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Long.milliSecondsToTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
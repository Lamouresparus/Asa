package com.android.asa.extensions

import android.content.res.Resources

class Number {
    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
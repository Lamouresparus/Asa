 package com.android.asa.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Patterns


 fun String.isEmailValid() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

 fun String.makeSpannable(start: Int, end: Int, color: Int) : SpannableString{

     val spannableText = SpannableString(this)
     spannableText.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
     return spannableText

 }
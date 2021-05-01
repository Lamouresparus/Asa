package com.android.asa.extensions

import android.text.TextUtils
import android.util.Patterns


fun String.isEmailValid() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
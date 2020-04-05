package com.android.asa.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.annotation.ColorInt;

public class ViewUtil {

    static public void spannableText(TextView view, String text, int start, int end, @ColorInt int color) {

        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(spannable);
    }
}

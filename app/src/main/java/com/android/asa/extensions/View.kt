package com.android.asa.extensions

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


fun ImageView.loadImage(image: Any?, placeholder: Int = 0, circular: Boolean = false) {
    Glide.with(context).load(image)
            .placeholder(placeholder)
            .apply(
                    if (circular) RequestOptions.circleCropTransform()
                    else RequestOptions.noTransformation()
            ).into(this)
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.snack(msg: CharSequence, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, length).show()
}

fun ViewGroup.inflate(viewType: Int, attacheToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(viewType, this, attacheToRoot)
}

fun TextInputLayout.showError(errorMessage: String) {
    error = errorMessage
}

fun TextInputLayout.disableError() {
    error = null
}


@RequiresApi(Build.VERSION_CODES.N)
fun View.showTimePicker(text: String, callback: (String) -> Unit) {
    val mcurrentTime: Calendar = Calendar.getInstance()
    val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
    val minute: Int = mcurrentTime.get(Calendar.MINUTE)
    val mTimePicker = TimePickerDialog(context,
            { _, selectedHour, selectedMinute ->
                callback.invoke("$selectedHour:$selectedMinute")

            }, hour, minute, false)

    mTimePicker.setTitle(text)
    mTimePicker.show()
}


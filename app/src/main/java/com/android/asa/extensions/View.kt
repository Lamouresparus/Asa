package com.android.asa.extensions

import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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


package com.android.asa.ui.widget

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class ViewHolder<T>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(element: T)
}


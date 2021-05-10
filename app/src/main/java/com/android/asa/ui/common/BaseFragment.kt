package com.android.asa.ui.common

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {


    private val disposableContainer = CompositeDisposable()

    val progressDialog by lazy {
        ProgressDialog(requireContext()).apply {
            setCanceledOnTouchOutside(false)
        }
    }


    fun Disposable.addToContainer() = disposableContainer.add(this)
}
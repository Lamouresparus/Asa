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

    fun showProgressDialog(message: String) {
        progressDialog.apply {
            setMessage(message)
            show()
        }
    }

    fun hideProgressDialog() = progressDialog.dismiss()


    fun Disposable.addToContainer() = disposableContainer.add(this)
}
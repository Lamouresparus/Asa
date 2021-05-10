package com.android.asa.ui.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseViewModel : ViewModel() {

    private val compositeContainer = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeContainer.clear()
    }

    fun Disposable.addToContainer() = compositeContainer.add(this)
}

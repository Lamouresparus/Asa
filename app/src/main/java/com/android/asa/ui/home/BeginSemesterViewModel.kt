package com.android.asa.ui.home

import androidx.lifecycle.MutableLiveData
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.utils.Result
import com.asa.domain.StartNewSemesterUseCase
import com.classic.chatapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class BeginSemesterViewModel @Inject constructor(
    private val startNewSemesterUseCase: StartNewSemesterUseCase
) : BaseViewModel() {

    private var _startNewSemester = MutableLiveData<Event<Result<Unit>>>()
    val startNewSemester = _startNewSemester.asLiveData()

    fun startNewSemester() {
        startNewSemesterUseCase
            .execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _startNewSemester.postValue(Event(Result.Loading))
            }.subscribe(
                {
                    _startNewSemester.postValue(Event(Result.Success()))
                },
                {
                    _startNewSemester.postValue(Event(Result.Error(it.message.toString())))
                }
            ).addToContainer()
    }

}
package com.android.asa.ui.reading_time_set_up

import androidx.lifecycle.MutableLiveData
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.utils.Result
import com.asa.domain.ReadingTimeSetUpUseCase
import com.android.asa.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ReadingTimeViewModel @Inject constructor(
        private val readingTimeSetUpUseCase: ReadingTimeSetUpUseCase
) : BaseViewModel() {

    private var _readingTimeSetUp = MutableLiveData<Event<Result<Unit>>>()
    val readingTimeSetUp = _readingTimeSetUp.asLiveData()


    fun uploadReadTimeSetUp(params: ReadingTimeSetUpUseCase.Params) {
        readingTimeSetUpUseCase
                .execute(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _readingTimeSetUp.postValue(Event(Result.Loading))
                }
                .subscribe(
                        {
                            _readingTimeSetUp.postValue(Event(Result.Success()))
                        },
                        {
                            _readingTimeSetUp.postValue(Event(Result.Error(it.message.toString())))
                        }
                ).addToContainer()


    }

    companion object {
        var preferredReadDay = 0
        var preferredReadTime = 0
        var averageReadingHours = 0
    }
}
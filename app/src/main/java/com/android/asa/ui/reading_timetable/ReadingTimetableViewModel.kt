package com.android.asa.ui.reading_timetable

import androidx.lifecycle.MutableLiveData
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.utils.Result
import com.asa.domain.GetReadingTimetableUseCase
import com.asa.domain.model.ReadingTimetableDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ReadingTimetableViewModel @Inject constructor(
    private val getReadingTimeTableUseCase: GetReadingTimetableUseCase
) : BaseViewModel() {

    private var _readingTimetable = MutableLiveData<Result<List<ReadingTimetableDomain>>>()
    val readingTimetable = _readingTimetable.asLiveData()

    fun getReadingTimeTable() {
        getReadingTimeTableUseCase
            .execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _readingTimetable.postValue((Result.Loading))
            }.subscribe(
                {
                    _readingTimetable.postValue((Result.Success(it)))
                },
                {
                    _readingTimetable.postValue((Result.Error(it.message.toString())))
                }
            ).addToContainer()
    }
}
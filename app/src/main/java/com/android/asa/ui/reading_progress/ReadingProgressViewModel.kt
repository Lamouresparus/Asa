package com.android.asa.ui.reading_progress

import androidx.lifecycle.MutableLiveData
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.utils.Result
import com.asa.domain.GetAverageReadingTimeUseCase
import com.asa.domain.GetOverallReadingTimeUseCase
import com.asa.domain.GetReadingTimetableUseCase
import com.asa.domain.GetTotalDailyReadingHoursUseCase
import com.asa.domain.GetTotalReadingTimeUseCase
import com.asa.domain.model.CourseTotalReadingHoursDomain
import com.asa.domain.model.DailyTotalReadingHoursDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

@HiltViewModel
class ReadingProgressViewModel @Inject constructor(
    private val getReadingTimeTableUseCase: GetReadingTimetableUseCase,
    private val getTotalDailyReadingHoursUseCase: GetTotalDailyReadingHoursUseCase,
    private val getOverallReadingTimeUseCase: GetOverallReadingTimeUseCase,
    private val getAverageReadingTimeUseCase: GetAverageReadingTimeUseCase,
    private val getTotalReadingTimeUseCase: GetTotalReadingTimeUseCase
) : BaseViewModel() {

    private var _viewContent = MutableLiveData<Result<ViewContent>>()
    val viewContent = _viewContent.asLiveData()

    fun getViewContent() {
        getReadingTimeTableUseCase
            .execute()
            .doOnSubscribe { _viewContent.postValue(Result.Loading) }
            .flatMap {
                Single.zip(
                    getTotalDailyReadingHoursUseCase.execute(it),
                    getOverallReadingTimeUseCase.execute(it),
                    getAverageReadingTimeUseCase.execute(it),
                    Single.just(getTotalReadingTimeUseCase.execute(it)),
                    { dailyReadingHours, overallReadingHours, averageReadingTime, totalReadingHours ->
                        ViewContent(
                            dailyReadingHours, overallReadingHours, averageReadingTime, totalReadingHours
                        )
                    }
                )
            }.subscribe({
                _viewContent.postValue(Result.Success(it))
            }, {
                _viewContent.postValue(Result.Error(it.message.toString()))
            }).addToContainer()
    }
}

data class ViewContent(
    val dailyReadingHours: List<DailyTotalReadingHoursDomain>,
    val overallReadingHours: Double,
    val averageReadingTime: Double,
    val totalReadingHours: List<CourseTotalReadingHoursDomain>
)
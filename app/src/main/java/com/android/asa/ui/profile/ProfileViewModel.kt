package com.android.asa.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.ui.countup_reading_timer_ui.UserCourses
import com.asa.data.sharedPreference.SharedPreferenceReader
import com.asa.data.sharedPreference.SharedPreferenceWriter
import com.asa.data.sources.DataSourceFactory
import com.asa.domain.GetReadingTimetableUseCase
import com.asa.domain.GetTotalReadingTimeUseCase
import com.asa.domain.model.CourseTotalReadingHoursDomain
import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserCoursesDomain
import com.asa.domain.model.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataSource: DataSourceFactory,
    private val prefWriter: SharedPreferenceWriter,
    private val preferenceReader: SharedPreferenceReader,
    private val getTotalReadingTimeUseCase: GetTotalReadingTimeUseCase,
    private val getReadingTimeTableUseCase: GetReadingTimetableUseCase
) : BaseViewModel() {

    fun saveUserCourse(userCourses: CourseTotalReadingHoursDomain) {
        prefWriter.saveUserCourse(userCourses.toUserCourseDomain())
    }

    var showTimerCountDown = false

    fun getUserCourse(): CourseTotalReadingHoursDomain = preferenceReader.getCourseDetail()!!.toUserCourse()

    private var _viewContent = MutableLiveData<ViewState>()
    val viewState = _viewContent.asLiveData()

    private val _user = MutableLiveData<UserDomain>()
    val user = _user.asLiveData()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = preferenceReader.getUserData()
            _user.postValue(user ?: UserDomain())
        }
    }

    fun getViewContent() {
        getReadingTimeTableUseCase
            .execute()
            .doOnSubscribe { _viewContent.postValue(ViewState.Loading) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                Single.just(getTotalReadingTimeUseCase.execute(it))
            }.subscribe({
                val semesterInfo = preferenceReader.getSemesterInformation()
                val viewState = ViewState.Content(it, semesterInfo?:SemesterDomain())
                _viewContent.postValue(viewState)
            }, {
                _viewContent.postValue(ViewState.Error(it.message.toString()))
            }).addToContainer()
    }

    fun CourseTotalReadingHoursDomain.toUserCourseDomain() = UserCoursesDomain(
        courseCode = course,
        CourseProgress = totalReadHours.toString()
    )

    private fun UserCoursesDomain.toUserCourse() = CourseTotalReadingHoursDomain(
        course = courseCode,
        totalReadHours = CourseProgress.toDouble()
    )

    sealed class ViewState {
        object Loading : ViewState()
        data class Content(
            val courses: List<CourseTotalReadingHoursDomain>,
            val semesterInfo: SemesterDomain
        ) : ViewState()

        data class Error(val message: String) : ViewState()
    }
}
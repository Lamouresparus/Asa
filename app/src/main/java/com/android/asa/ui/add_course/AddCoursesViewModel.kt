package com.android.asa.ui.add_course

import androidx.lifecycle.MutableLiveData
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.utils.Result
import com.asa.domain.AddCourseUseCase
import com.asa.domain.GetUserCoursesUseCase
import com.asa.domain.model.CourseDomain
import com.android.asa.utils.Event
import com.asa.domain.UploadReadingTimetableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AddCoursesViewModel @Inject constructor(
        private val addCourseUseCase: AddCourseUseCase,
        private val coursesUseCase: GetUserCoursesUseCase,
        private val readingTimetableUseCase: UploadReadingTimetableUseCase
) : BaseViewModel() {

    private var _addCourse = MutableLiveData<Event<Result<Unit>>>()
    val addCourse = _addCourse.asLiveData()

    private var _courses = MutableLiveData<Result<List<CourseDomain>>>()
    val courses = _courses.asLiveData()

    private var _uploadReadingTimetable = MutableLiveData<Event<Result<Unit>>>()
    val uploadReadingTimetable = _uploadReadingTimetable.asLiveData()

    fun saveCourses(params: AddCourseUseCase.Params) {
        addCourseUseCase
                .execute(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _addCourse.postValue(Event(Result.Loading))
                }.subscribe({
                    _addCourse.postValue(Event(Result.Success()))
                }, {
                    _addCourse.postValue(Event(Result.Error(it.message.toString())))
                }).addToContainer()

    }


    fun getCourses() {
        coursesUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _courses.postValue((Result.Loading))
                }.subscribe({
                    _courses.postValue((Result.Success(it)))
                }, {
                    _courses.postValue((Result.Error(it.message.toString())))
                }).addToContainer()


    }

    fun uploadReadingTimetable(params: UploadReadingTimetableUseCase.Params){
        readingTimetableUseCase.execute(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _uploadReadingTimetable.postValue(Event(Result.Loading))
            }.subscribe({
                _uploadReadingTimetable.postValue(Event(Result.Success()))
            }, {
                _uploadReadingTimetable.postValue(Event(Result.Error(it.message.toString())))
            }).addToContainer()
    }
}
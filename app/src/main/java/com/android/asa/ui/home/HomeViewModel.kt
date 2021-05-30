package com.android.asa.ui.home

import androidx.lifecycle.MutableLiveData
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.utils.Result
import com.asa.domain.GetCoursesForCurrentDayUseCase
import com.asa.domain.model.CourseDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        private val coursesForCurrentDayUseCase: GetCoursesForCurrentDayUseCase,
) : BaseViewModel() {

    private var _todayCourses = MutableLiveData<Result<List<CourseDomain>>>()
    val todayCourses = _todayCourses.asLiveData()


    fun getCoursesForToday() {
        coursesForCurrentDayUseCase
            .execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _todayCourses.postValue((Result.Loading))
            }.subscribe(
                {
                    _todayCourses.postValue((Result.Success(it)))
                },
                        {
                            _todayCourses.postValue((Result.Error(it.message.toString())))
                        }
                ).addToContainer()
    }

}
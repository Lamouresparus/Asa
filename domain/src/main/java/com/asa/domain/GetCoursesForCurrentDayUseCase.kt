package com.asa.domain

import com.asa.domain.model.CourseDomain
import com.asa.domain.repository.CourseRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCoursesForCurrentDayUseCase @Inject constructor(private val courseRepository: CourseRepository) :
        BaseUseCase<GetCoursesForCurrentDayUseCase.Params, Single<List<CourseDomain>>> {

    override fun execute(param: Params?): Single<List<CourseDomain>> {
        param ?: throw UnsupportedOperationException("login params not provided")
        return courseRepository.getCoursesForToday(param)
    }

    data class Params(
            val courses: List<CourseDomain>
    )
}
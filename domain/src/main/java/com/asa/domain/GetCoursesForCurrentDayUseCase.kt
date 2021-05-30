package com.asa.domain

import com.asa.domain.model.CourseDomain
import com.asa.domain.repository.CourseRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCoursesForCurrentDayUseCase @Inject constructor(private val courseRepository: CourseRepository) :
        BaseUseCase<GetCoursesForCurrentDayUseCase.Params, Single<List<CourseDomain>>> {

    override fun execute(param: Params?): Single<List<CourseDomain>> {
        return courseRepository.getCoursesForToday()
    }

    data class Params(
            val day: String
    )
}
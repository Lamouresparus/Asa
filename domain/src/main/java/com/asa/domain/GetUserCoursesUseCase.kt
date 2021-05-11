package com.asa.domain

import com.asa.domain.model.CourseDomain
import com.asa.domain.repository.CourseRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUserCoursesUseCase @Inject constructor(private val courseRepository: CourseRepository) :
        BaseUseCase<GetUserCoursesUseCase.Params, Single<List<CourseDomain>>> {

    override fun execute(param: Params?): Single<List<CourseDomain>> {
        return courseRepository.getUserCourses()
    }

    data class Params(
            val course: CourseDomain
    )
}
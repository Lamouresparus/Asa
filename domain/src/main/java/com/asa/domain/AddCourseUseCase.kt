package com.asa.domain

import com.asa.domain.model.CourseDomain
import com.asa.domain.repository.CourseRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddCourseUseCase @Inject constructor(private val courseRepository: CourseRepository) :
        BaseUseCase<AddCourseUseCase.Params, Completable> {

    override fun execute(param: Params?): Completable {
        param ?: throw UnsupportedOperationException("login params not provided")
        return courseRepository.saveCourses(param)
    }

    data class Params(
            val course: CourseDomain
    )
}
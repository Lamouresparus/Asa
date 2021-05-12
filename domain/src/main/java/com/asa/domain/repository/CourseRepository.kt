package com.asa.domain.repository

import com.asa.domain.AddCourseUseCase
import com.asa.domain.model.CourseDomain
import io.reactivex.Completable
import io.reactivex.Single

interface CourseRepository {
    fun saveCourses(params: AddCourseUseCase.Params): Completable
    fun getCoursesForToday(): Single<List<CourseDomain>>
    fun getUserCourses(): Single<List<CourseDomain>>
}
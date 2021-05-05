package com.asa.domain.repository

import com.asa.domain.AddCoursesUseCase
import com.asa.domain.model.CourseDomain
import io.reactivex.Completable
import io.reactivex.Single

interface CourseRepository {
    fun saveCourses(params: AddCoursesUseCase.Params): Completable
    fun getCoursesForToday(): Single<List<CourseDomain>>
}
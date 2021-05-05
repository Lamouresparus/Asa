package com.asa.domain.repository

import com.asa.domain.AddCoursesUseCase
import io.reactivex.Completable

interface CourseRepository {
    fun saveCourses(params: AddCoursesUseCase.Params): Completable
}
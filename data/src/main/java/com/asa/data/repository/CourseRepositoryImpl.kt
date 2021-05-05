package com.asa.data.repository

import com.asa.data.sources.DataSourceFactory
import com.asa.domain.AddCoursesUseCase
import com.asa.domain.repository.CourseRepository
import io.reactivex.Completable
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
        private val dataSource: DataSourceFactory,
) : CourseRepository {
    override fun saveCourses(params: AddCoursesUseCase.Params): Completable {
        return dataSource.remote().saveCourses(params)
    }
}
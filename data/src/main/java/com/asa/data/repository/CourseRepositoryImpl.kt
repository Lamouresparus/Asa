package com.asa.data.repository

import com.asa.data.sources.DataSourceFactory
import com.asa.domain.AddCoursesUseCase
import com.asa.domain.model.CourseDomain
import com.asa.domain.repository.CourseRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
        private val dataSource: DataSourceFactory,
) : CourseRepository {
    override fun saveCourses(params: AddCoursesUseCase.Params): Completable {
        return dataSource.remote().saveCourses(params)
    }


    override fun getCoursesForToday(): Single<List<CourseDomain>> {

        return dataSource.remote().getCoursesForToday()
    }
}
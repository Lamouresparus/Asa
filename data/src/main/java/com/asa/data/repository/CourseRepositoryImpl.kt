package com.asa.data.repository

import com.asa.data.sharedPreference.SharedPreferenceReader
import com.asa.data.sharedPreference.SharedPreferenceWriter
import com.asa.data.sources.DataSourceFactory
import com.asa.domain.AddCourseUseCase
import com.asa.domain.model.CourseDomain
import com.asa.domain.model.SemesterDomain
import com.asa.domain.repository.CourseRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val dataSource: DataSourceFactory,
    private val prefWriter: SharedPreferenceWriter,
    private val prefReader: SharedPreferenceReader
) : CourseRepository {
    override fun saveCourses(params: AddCourseUseCase.Params): Completable {
        return dataSource.remote().saveCourses(params).doOnComplete {
            /**
             * increment the noOfCoursesOffered locally when a course was successfully added
             */
            val semesterInfo = prefReader.getSemesterInformation()
            val updatedSemesterInfo =
                SemesterDomain(
                    semesterInfo?.hasSemesterBegun ?: false,
                    semesterInfo?.noOfCoursesOffered ?: 0 + 1
                )
            prefWriter.saveSemesterInformation(updatedSemesterInfo)
        }
    }

    override fun getCoursesForToday(): Single<List<CourseDomain>> {
        return dataSource.remote().getCoursesForToday()
    }

    override fun getUserCourses(): Single<List<CourseDomain>> {
        return dataSource.remote().getUserCourses()
    }
}
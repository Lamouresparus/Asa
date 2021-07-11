package com.asa.domain

import com.asa.domain.model.ReadingTimetableDomain
import io.reactivex.Single
import javax.inject.Inject

class GetAverageReadingTimeUseCase @Inject constructor() : BaseUseCase<List<ReadingTimetableDomain>, Single<Double>> {
    override fun execute(param: List<ReadingTimetableDomain>?): Single<Double> {
        val courses = mutableListOf<String>()
        val averageReadTime: Double
        var overallReadingTime = 0.0
        if (!param.isNullOrEmpty()) {
            for (readingTimeTable in param) {
                for (course in readingTimeTable.courses) {
                    overallReadingTime += course.totalReadingHours
                    courses.add(course.courseCode)
                }
            }
        }
        courses.distinct()
        averageReadTime = overallReadingTime / courses.size

        return Single.just(averageReadTime)
    }
}
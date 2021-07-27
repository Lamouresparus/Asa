package com.asa.domain

import com.asa.domain.model.CourseTotalReadingHoursDomain
import com.asa.domain.model.ReadingTimeDomain
import com.asa.domain.model.ReadingTimetableDomain
import javax.inject.Inject

class GetTotalReadingTimeUseCase @Inject constructor() : BaseUseCase<List<ReadingTimetableDomain>, List<CourseTotalReadingHoursDomain>> {
    override fun execute(param: List<ReadingTimetableDomain>?): List<CourseTotalReadingHoursDomain> {
        val courses = mutableListOf<String>()
        val coursesTotalReadHours = mutableListOf<CourseTotalReadingHoursDomain>()
        val readTimes = mutableListOf<ReadingTimeDomain>()

        if (!param.isNullOrEmpty()) {
            for (readTimeTable in param) {
                for (readTime in readTimeTable.courses) {
                    courses.add(readTime.courseCode)
                    readTimes.add(readTime)
                }
            }
            val courseListWithNoDuplication = courses.distinct()
            var totalReadTime = 0.0
            for (course in courseListWithNoDuplication) {
                for (readTime in readTimes) {
                    if (readTime.courseCode == course) {
                        totalReadTime += readTime.totalReadingHours
                    }
                }
                coursesTotalReadHours.add(CourseTotalReadingHoursDomain(course, totalReadTime))
                totalReadTime = 0.0
            }
        }
        return coursesTotalReadHours
    }
}
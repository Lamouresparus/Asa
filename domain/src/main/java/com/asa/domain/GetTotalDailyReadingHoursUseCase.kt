package com.asa.domain

import com.asa.domain.model.DailyTotalReadingHoursDomain
import com.asa.domain.model.ReadingTimetableDomain
import io.reactivex.Single
import javax.inject.Inject

class GetTotalDailyReadingHoursUseCase @Inject constructor() : BaseUseCase<List<ReadingTimetableDomain>, Single<List<DailyTotalReadingHoursDomain>>> {
    override fun execute(param: List<ReadingTimetableDomain>?): Single<List<DailyTotalReadingHoursDomain>> {
        val totalDailyReadHours = mutableListOf<DailyTotalReadingHoursDomain>()
        var totalReadTime = 0.0

        if (!param.isNullOrEmpty()) {
            for (readingTime in param) {
                for (course in readingTime.courses) {
                    totalReadTime += course.totalReadingHours
                }
                totalDailyReadHours.add(DailyTotalReadingHoursDomain(readingTime.day, totalReadTime))
                totalReadTime = 0.0
            }
        }

        return Single.just(totalDailyReadHours)
    }
}
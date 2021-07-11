package com.asa.domain

import com.asa.domain.model.ReadingTimetableDomain
import io.reactivex.Single
import javax.inject.Inject

class GetOverallReadingTimeUseCase @Inject constructor() : BaseUseCase<List<ReadingTimetableDomain>, Single<Double>> {
    override fun execute(param: List<ReadingTimetableDomain>?): Single<Double> {
        var overallReadingTime = 0.0
        if (!param.isNullOrEmpty()) {
            for (readingTimeTable in param) {
                for (course in readingTimeTable.courses) {
                    overallReadingTime += course.totalReadingHours
                }
            }
        }

        return Single.just(overallReadingTime)
    }
}
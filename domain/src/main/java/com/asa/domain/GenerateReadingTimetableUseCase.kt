package com.asa.domain

import com.asa.domain.model.CourseDomain
import com.asa.domain.model.ReadingTimeDomain
import io.reactivex.Single
import javax.inject.Inject

class GenerateReadingTimetableUseCase @Inject constructor(private val readingTimetableManager: ReadingTimetableManager) :
    BaseUseCase<GenerateReadingTimetableUseCase.Params, Single<List<ReadingTimeDomain>>> {

    data class Params(
        val courses: List<CourseDomain>,
        val prefReadDay: Int,
        val prefReadTime: Int,
        val avrgReadHours: Int

    )

    override fun execute(param: Params?): Single<List<ReadingTimeDomain>> {

        return Single.create { emitter ->

            if (param == null) {
                emitter.onError(Throwable("No user courses"))
                return@create
            }
            val readingTimetable = readingTimetableManager.generateReadingTimeTable(param.courses, param.prefReadDay, param.prefReadTime, param.avrgReadHours)
            if (readingTimetable.isNullOrEmpty()) {
                emitter.onError(Throwable("Could not generate reading timetable"))
            } else emitter.onSuccess(readingTimetable)

        }
    }
}

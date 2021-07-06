package com.asa.domain

import com.asa.domain.model.ReadingTimeDomain
import com.asa.domain.repository.ReadingTimetableRepository
import io.reactivex.Completable
import javax.inject.Inject

class GenerateReadingTimetableUseCase @Inject constructor(private val readingTimetableRepository: ReadingTimetableRepository) :
    BaseUseCase<GenerateReadingTimetableUseCase.Params, Completable> {

    override fun execute(param: Params?): Completable {
    }

    data class Params(
        val readingTimetable: List<ReadingTimeDomain>
    )
}

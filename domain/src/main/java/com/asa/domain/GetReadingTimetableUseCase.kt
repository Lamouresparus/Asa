package com.asa.domain

import com.asa.domain.model.ReadingTimetableDomain
import com.asa.domain.repository.ReadingTimetableRepository
import io.reactivex.Single
import javax.inject.Inject

class GetReadingTimetableUseCase @Inject constructor(private val readingTimetableRepository: ReadingTimetableRepository) : BaseUseCase<Unit, Single<List<ReadingTimetableDomain>>> {
    override fun execute(param: Unit?): Single<List<ReadingTimetableDomain>> {
        return readingTimetableRepository.getReadingTimetable()
    }
}
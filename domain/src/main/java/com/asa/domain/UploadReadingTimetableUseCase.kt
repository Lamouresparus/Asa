package com.asa.domain

import com.asa.domain.model.ReadingTimeDomain
import com.asa.domain.repository.ReadingTimetableRepository
import io.reactivex.Completable
import javax.inject.Inject

class UploadReadingTimetableUseCase @Inject constructor(private val readingTimetableRepository: ReadingTimetableRepository) :
BaseUseCase<UploadReadingTimetableUseCase.Params, Completable>{


    override fun execute(param: Params?): Completable {
        param ?: throw UnsupportedOperationException("no course added")
        return readingTimetableRepository.uploadReadingTimetable(param)
    }

    data class Params(
        val readingTimetable: List<ReadingTimeDomain>
    )
}
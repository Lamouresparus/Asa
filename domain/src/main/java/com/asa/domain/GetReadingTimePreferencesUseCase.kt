package com.asa.domain

import com.asa.domain.model.ReadingTimePreferencesDomain
import com.asa.domain.repository.ReadingTimetableRepository
import io.reactivex.Single
import javax.inject.Inject

class GetReadingTimePreferencesUseCase  @Inject constructor(private val readingTimetableRepository: ReadingTimetableRepository) :
    BaseUseCase<Unit, Single<ReadingTimePreferencesDomain>> {
    override fun execute(param: Unit?): Single<ReadingTimePreferencesDomain> {
        return readingTimetableRepository.getReadingPreferences()
    }
}
package com.asa.domain.repository

import com.asa.domain.UploadReadingTimetableUseCase
import com.asa.domain.model.ReadingTimePreferencesDomain
import com.asa.domain.model.ReadingTimetableDomain
import io.reactivex.Completable
import io.reactivex.Single

interface ReadingTimetableRepository {
    fun uploadReadingTimetable(params: UploadReadingTimetableUseCase.Params): Completable
    fun getReadingPreferences(): Single<ReadingTimePreferencesDomain>
    fun getReadingTimetable(): Single<List<ReadingTimetableDomain>>
}
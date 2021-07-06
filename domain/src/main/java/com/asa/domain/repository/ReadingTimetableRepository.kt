package com.asa.domain.repository

import com.asa.domain.UploadReadingTimetableUseCase
import io.reactivex.Completable

interface ReadingTimetableRepository {
    fun uploadReadingTimetable(params: UploadReadingTimetableUseCase.Params): Completable
}
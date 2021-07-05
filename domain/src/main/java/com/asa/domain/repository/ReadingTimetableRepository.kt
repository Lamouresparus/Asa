package com.asa.domain.repository

import com.asa.domain.CreateReadingTimetableUseCase
import io.reactivex.Completable

interface ReadingTimetableRepository {
    fun createReadingTimetable(params: CreateReadingTimetableUseCase.Params): Completable
}
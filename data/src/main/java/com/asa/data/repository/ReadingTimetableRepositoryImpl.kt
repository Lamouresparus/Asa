package com.asa.data.repository

import com.asa.data.sources.DataSourceFactory
import com.asa.domain.UploadReadingTimetableUseCase
import com.asa.domain.repository.ReadingTimetableRepository
import io.reactivex.Completable
import javax.inject.Inject

class ReadingTimetableRepositoryImpl @Inject constructor(
    private val dataSource: DataSourceFactory
) : ReadingTimetableRepository {

    override fun uploadReadingTimetable(params: UploadReadingTimetableUseCase.Params): Completable {
        return dataSource.remote().uploadReadingTimetable(params)
        //Use do on success and ave to pref reader?
    }
}
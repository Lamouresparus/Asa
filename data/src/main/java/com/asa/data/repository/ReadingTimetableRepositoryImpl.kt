package com.asa.data.repository

import com.asa.data.sources.DataSourceFactory
import com.asa.domain.CreateReadingTimetableUseCase
import com.asa.domain.repository.ReadingTimetableRepository
import io.reactivex.Completable
import javax.inject.Inject

class ReadingTimetableRepositoryImpl @Inject constructor(
    private val dataSource: DataSourceFactory
) : ReadingTimetableRepository {


    override fun createReadingTimetable(params: CreateReadingTimetableUseCase.Params): Completable {
        return dataSource.remote().createReadingTimetable(params)
        //Use do on success and ave to pref reader?
    }
}
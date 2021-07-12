package com.asa.data.repository

import com.asa.data.sources.DataSourceFactory
import com.asa.domain.ReadingTimeSetUpUseCase
import com.asa.domain.UploadReadingTimetableUseCase
import com.asa.domain.model.ReadingTimePreferencesDomain
import com.asa.domain.model.ReadingTimetableDomain
import com.asa.domain.repository.ReadingTimetableRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ReadingTimetableRepositoryImpl @Inject constructor(
    private val dataSource: DataSourceFactory
) : ReadingTimetableRepository {

    override fun uploadReadingTimetable(params: UploadReadingTimetableUseCase.Params): Completable {
        return dataSource.remote().uploadReadingTimetable(params)
        //Use do on success and ave to pref reader?
    }

    override fun getReadingPreferences(): Single<ReadingTimePreferencesDomain> {
        return dataSource.remote().getReadingPreferences()
    }

    override fun getReadingTimetable(): Single<List<ReadingTimetableDomain>> {
        return dataSource.remote().getReadingTimetable()
    }
}
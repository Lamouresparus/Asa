package com.asa.data.repository

import com.asa.data.sharedPreference.SharedPreferenceReader
import com.asa.data.sharedPreference.SharedPreferenceWriter
import com.asa.data.sources.DataSourceFactory
import com.asa.domain.model.SemesterDomain
import com.asa.domain.repository.SemesterRepository
import io.reactivex.Completable
import javax.inject.Inject

class SemesterRepositoryImpl @Inject constructor(
    private val dataSource: DataSourceFactory,
    private val prefWriter: SharedPreferenceWriter,
    private val preferenceReader: SharedPreferenceReader
) : SemesterRepository {

    override fun startNewSemester(): Completable {
        val user =
            preferenceReader.getUserData() ?: throw UnsupportedOperationException("user not found")
        return dataSource.remote().startNewSemester(user.userId).doOnComplete {
            prefWriter.saveSemesterInformation(SemesterDomain(true))
        }
    }
}
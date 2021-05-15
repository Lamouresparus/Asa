package com.asa.data.repository

import com.asa.data.sharedPreference.SharedPreferenceWriter
import com.asa.data.sources.DataSourceFactory
import com.asa.domain.LogInUseCase
import com.asa.domain.ReadingTimeSetUpUseCase
import com.asa.domain.RegisterUseCase
import com.asa.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: DataSourceFactory,
    private val prefWriter: SharedPreferenceWriter
) : UserRepository {
    override fun login(params: LogInUseCase.Params): Completable {
        return dataSource.remote().login(params).doOnSuccess {
            prefWriter.apply {
                saveUser(it)
            }
        }.ignoreElement()
    }

    override fun register(param: RegisterUseCase.Params): Completable {
        return dataSource.remote().register(param).doOnSuccess {
            //save user to room db
        }.ignoreElement()
    }

    override fun logOut(): Completable {
        return Completable.fromAction {
            dataSource.remote().logOut().subscribe()
//          dataSource.local().logOut().subscribe()
        }
    }


    override fun saveReadingTime(params: ReadingTimeSetUpUseCase.Params): Completable {

        return dataSource.remote().saveReadingTime(params)
    }
}
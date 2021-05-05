package com.asa.data.repository

import com.asa.data.sources.DataSourceFactory
import com.asa.domain.LogInUseCase
import com.asa.domain.repository.RegisterUseCase
import com.asa.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val dataSource: DataSourceFactory,
) : UserRepository {
    override fun login(params: LogInUseCase.Params): Completable {
        return dataSource.remote().login(params).doOnSuccess {
            //save user to room db
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
}
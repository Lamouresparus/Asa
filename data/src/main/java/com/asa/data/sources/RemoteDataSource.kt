package com.asa.data.sources

import com.asa.domain.LogInUseCase
import com.asa.domain.RegisterUseCase
import com.asa.domain.model.UserDomain
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteDataSource {
    fun login(params: LogInUseCase.Params): Single<UserDomain>
    fun register(param: RegisterUseCase.Params): Single<UserDomain>
    fun logOut(): Completable
}
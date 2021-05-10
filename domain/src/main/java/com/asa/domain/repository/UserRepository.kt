package com.asa.domain.repository

import com.asa.domain.LogInUseCase
import com.asa.domain.ReadingTimeSetUpUseCase
import com.asa.domain.RegisterUseCase
import io.reactivex.Completable

interface UserRepository {
    fun login(params: LogInUseCase.Params): Completable

    fun register(param: RegisterUseCase.Params): Completable

    fun logOut(): Completable

    fun saveReadingTime(params: ReadingTimeSetUpUseCase.Params): Completable
}
package com.asa.domain.repository

import com.asa.domain.LogInUseCase
import io.reactivex.Completable

interface UserRepository {
    fun login(params: LogInUseCase.Params): Completable
}
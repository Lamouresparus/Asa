package com.asa.data.sources

import com.asa.domain.AddCoursesUseCase
import com.asa.domain.LogInUseCase
import com.asa.domain.model.UserDomain
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteDataSource {
    fun login(params: LogInUseCase.Params): Single<UserDomain>

    fun saveCourses(params: AddCoursesUseCase.Params): Completable
}
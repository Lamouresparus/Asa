package com.asa.data.sources

import com.asa.domain.AddCoursesUseCase
import com.asa.domain.LogInUseCase
import com.asa.domain.ReadingTimeSetUpUseCase
import com.asa.domain.RegisterUseCase
import com.asa.domain.model.CourseDomain
import com.asa.domain.model.UserDomain
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteDataSource {
    fun login(params: LogInUseCase.Params): Single<UserDomain>
    fun register(param: RegisterUseCase.Params): Single<UserDomain>
    fun logOut(): Completable
    fun saveReadingTime(params: ReadingTimeSetUpUseCase.Params): Completable
    fun saveCourses(params: AddCoursesUseCase.Params): Completable
    fun getCoursesForToday(): Single<List<CourseDomain>>
}
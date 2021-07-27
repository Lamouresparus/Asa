package com.asa.data.sources

import com.asa.domain.AddCourseUseCase
import com.asa.domain.UploadReadingTimetableUseCase
import com.asa.domain.LogInUseCase
import com.asa.domain.ReadingTimeSetUpUseCase
import com.asa.domain.RegisterUseCase
import com.asa.domain.model.CourseDomain
import com.asa.domain.model.ReadingTimePreferencesDomain
import com.asa.domain.model.ReadingTimetableDomain
import com.asa.domain.model.SemesterDomain
import com.asa.domain.model.UserDomain
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteDataSource {
    fun login(params: LogInUseCase.Params): Single<Pair<UserDomain, SemesterDomain>>
    fun register(param: RegisterUseCase.Params): Single<UserDomain>
    fun logOut(): Completable
    fun saveReadingTime(params: ReadingTimeSetUpUseCase.Params): Completable
    fun saveCourses(params: AddCourseUseCase.Params): Single<SemesterDomain>
    fun getCoursesForToday(): Single<List<CourseDomain>>
    fun getUserCourses(): Single<List<CourseDomain>>
    fun startNewSemester(userId: String): Completable
    fun uploadReadingTimetable(params: UploadReadingTimetableUseCase.Params): Completable
    fun getReadingPreferences(): Single<ReadingTimePreferencesDomain>
    fun getReadingTimetable(): Single<List<ReadingTimetableDomain>>
    fun updateUserData(param: UserDomain): Single<UserDomain>
    fun getAllStudents(): Single<List<UserDomain>>
}
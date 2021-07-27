package com.android.asa.di

import com.asa.data.repository.CourseRepositoryImpl
import com.asa.data.repository.ReadingTimetableRepositoryImpl
import com.asa.data.repository.SemesterRepositoryImpl
import com.asa.data.repository.UserRepositoryImpl
import com.asa.domain.repository.CourseRepository
import com.asa.domain.repository.ReadingTimetableRepository
import com.asa.domain.repository.SemesterRepository
import com.asa.domain.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun provideCourseRepository(courseRepositoryImpl: CourseRepositoryImpl): CourseRepository

    @Binds
    @Singleton
    abstract fun provideSemesterRepository(semesterRepositoryImpl: SemesterRepositoryImpl): SemesterRepository

    @Binds
    @Singleton
    abstract fun provideReadingTimetableRepository(readingTimetableRepositoryImpl: ReadingTimetableRepositoryImpl): ReadingTimetableRepository

    companion object {

        @Singleton
        @Provides
        fun provideGson(): Gson {
            return GsonBuilder().create()
        }
    }
}

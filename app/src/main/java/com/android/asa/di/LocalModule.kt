package com.android.asa.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

//    @Binds
//    @Singleton
//    abstract fun remoteDataSource(localDataSource: LocalDataSource): RemoteDataSource


}
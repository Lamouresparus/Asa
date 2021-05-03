package com.android.asa.di

import com.asa.data.sources.RemoteDataSource
import com.asa.remotes.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun remoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource


}

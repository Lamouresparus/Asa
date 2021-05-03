package com.asa.data.sources

import javax.inject.Inject

class DataSourceFactory @Inject constructor(
        private val localDataSource: LocalDataSource,
        private val remoteDataSource: RemoteDataSource
) {

    fun local(): LocalDataSource {
        return localDataSource
    }

    fun remote(): RemoteDataSource {
        return remoteDataSource
    }
}
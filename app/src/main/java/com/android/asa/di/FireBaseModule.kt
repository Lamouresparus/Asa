package com.android.asa.di

import com.asa.remotes.RemoteDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FireBaseModule {

    @Singleton
    @Provides
    fun firebaseAuth(remoteDataSourceImpl: RemoteDataSourceImpl): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

}

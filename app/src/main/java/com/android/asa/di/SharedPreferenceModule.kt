package com.android.asa.di

import android.content.Context
import android.content.SharedPreferences
import com.asa.data.sharedPreference.SharedPreferenceKeys
import com.asa.data.sharedPreference.SharedPreferenceReader
import com.asa.data.sharedPreference.SharedPreferenceWriter
import com.asa.domain.model.UserDomain
import com.asa.local.sharedPreference.SharedPreferenceReaderImpl
import com.asa.local.sharedPreference.SharedPreferenceWriterImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    @Singleton
    abstract fun sharedPreferenceWriter(sharedPreferenceWriterImpl: SharedPreferenceWriterImpl):
            SharedPreferenceWriter

    @Binds
    @Singleton
    abstract fun sharedPreferenceReader(sharedPreferenceReaderImpl: SharedPreferenceReaderImpl):
            SharedPreferenceReader
}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideAndroidSharedPreference(
        @ApplicationContext context: Context,
        keys: SharedPreferenceKeys
    ): SharedPreferences {
        return context.getSharedPreferences(keys.ASA, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceKeys(): SharedPreferenceKeys {
        return SharedPreferenceKeys()
    }

    @Provides
    @Singleton
    fun provideUserFromSharedPreference(reader: SharedPreferenceReader): UserDomain? {
        return reader.getUserData()
    }
}
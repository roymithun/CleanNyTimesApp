package com.inhouse.cleannytimesapp.base.di

import com.inhouse.cleannytimesapp.navigation.NavManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {
    @Singleton
    @Provides
    fun navManager() = NavManager()
}
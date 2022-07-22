package com.inhouse.cleannytimesapp.data.di

import com.inhouse.cleannytimesapp.data.repository.MostPopularArticlesRepository
import com.inhouse.cleannytimesapp.domain.repository.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providerUserRepository(repository: MostPopularArticlesRepository): ArticlesRepository {
        return repository
    }
}
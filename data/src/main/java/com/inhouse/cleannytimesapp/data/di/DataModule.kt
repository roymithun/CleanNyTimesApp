package com.inhouse.cleannytimesapp.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.inhouse.cleannytimesapp.data.Constants.DATABASE_NAME
import com.inhouse.cleannytimesapp.data.local.db.ArticleDao
import com.inhouse.cleannytimesapp.data.local.db.ArticleDatabase
import com.inhouse.cleannytimesapp.data.local.db.RoomTypeConverters
import com.inhouse.cleannytimesapp.data.repository.MostPopularArticlesRepository
import com.inhouse.cleannytimesapp.domain.repository.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun typeConverter(
        gson: Gson
    ) = RoomTypeConverters(gson)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setPrettyPrinting().create()

    @Singleton
    @Provides
    fun articleDao(
        @ApplicationContext context: Context,
        typeConverters: RoomTypeConverters
    ): ArticleDao =
        Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            DATABASE_NAME
        ).addTypeConverter(typeConverters).build().articleDao()

    @Provides
    @Singleton
    fun providerUserRepository(
        repository: MostPopularArticlesRepository
    ): ArticlesRepository {
        return repository
    }
}
package com.inhouse.cleannytimesapp.data.di

import com.inhouse.cleannytimesapp.data.HttpClient
import com.inhouse.cleannytimesapp.data.remote.api.ArticleListApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun moshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun retrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(HttpClient.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Singleton
    @Provides
    fun provideArticleListApi(retrofit: Retrofit): ArticleListApi =
        retrofit.create(ArticleListApi::class.java)

    @Singleton
    @Provides
    internal fun providesHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(HttpClient.CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(HttpClient.READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(HttpClient.WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)

        return builder.build()
    }
}
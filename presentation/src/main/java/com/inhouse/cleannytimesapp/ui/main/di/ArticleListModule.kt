package com.inhouse.cleannytimesapp.ui.main.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ArticleListModule {
//    @ViewModelScoped
//    @Binds
//    abstract fun bindArticleRepository(repository: NyArticleRepository): ArticleRepository
}
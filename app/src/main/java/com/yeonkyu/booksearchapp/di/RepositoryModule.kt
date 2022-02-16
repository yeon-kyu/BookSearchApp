package com.yeonkyu.booksearchapp.di

import com.yeonkyu.booksearchapp.repository.SearchRepository
import com.yeonkyu.booksearchapp.repository.SearchRepositoryImpl
import com.yeonkyu.itbooksdk.ItBookStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideItBook() : ItBookStore {
        return ItBookStore
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        itBookStore: ItBookStore
    ): SearchRepository {
        return SearchRepositoryImpl(itBookStore)
    }
}